package com.gotrash.service;

import com.gotrash.api.v1.model.*;
import com.gotrash.api.v1.transformer.ExchangeTransformer;
import com.gotrash.api.v1.transformer.GroupTransformer;
import com.gotrash.api.v1.transformer.RewardTransformer;
import com.gotrash.api.v1.transformer.UserTransformer;
import com.gotrash.entity.ExchangeEntity;
import com.gotrash.entity.GroupEntity;
import com.gotrash.entity.GroupMemberEntity;
import com.gotrash.repository.GroupRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class GroupService {

  private final GroupRepository groupRepository;
  private final GroupMemberService groupMemberService;
  private final RewardService rewardService;
  private final UserService userService;

  @Transactional
  public Group save(Group group) {
    Reward reward = rewardService.getRewardByRewardId(group.getReward().getRewardId());
    User user = userService.getUserByUserId(group.getOwner().getUserId());
    group.setReward(reward);
    group.setOwner(user);

    // Create group
    GroupEntity groupEntity = groupRepository.save(GroupTransformer.transformModelToEntity(group));

    // Create group member
    GroupMember groupMember = GroupMember.builder()
        .user(user)
        .group(GroupTransformer.transformEntityToModel(groupEntity))
        .build();

    groupMember = groupMemberService.save(groupMember);

    // Set reference group member
    group = GroupTransformer.transformEntityToModel(groupEntity);
    group.setGroupMembers(List.of(groupMember));

    return group;
  }

  @Transactional
  public void addMember(GroupMember groupMember) {

    Group group = getGroupByGroupId(groupMember.getGroup().getGroupId());
    User user = userService.getUserByUserId(groupMember.getUser().getUserId());

    groupMember.setGroup(group);
    groupMember.setUser(user);

    groupMemberService.save(groupMember);
  }

  @Transactional
  public void removeMember(GroupMember groupMember) {
    GroupEntity groupEntity = groupRepository.findById(UUID.fromString(groupMember.getGroup().getGroupId()))
        .orElseThrow(() -> new EntityNotFoundException("Group not found"));
    User user = userService.getUserByUserId(groupMember.getUser().getUserId());

    GroupMemberEntity memberToRemove = groupEntity.getGroupMembers().stream()
        .filter(member -> member.getUser().getUserId().equals(UUID.fromString(user.getUserId())))
        .findFirst()
        .orElseThrow(() -> new EntityNotFoundException("Group member not found"));

    groupEntity.getGroupMembers().remove(memberToRemove);

    groupMember.setGroupMemberId(memberToRemove.getGroupMemberId().toString());
    groupMember.setGroup(null);
    groupMemberService.delete(groupMember.getGroupMemberId());
  }

  public List<Group> getGroups() {
    List<GroupEntity> groupEntities = groupRepository.findAll();

    return groupEntities.stream()
        .map(GroupTransformer::transformEntityToModel)
        .toList();
  }

  public Group getGroupByGroupId(String groupId) {
    Optional<GroupEntity> groupEntityOptional = groupRepository.findById(UUID.fromString(groupId));

    if (groupEntityOptional.isEmpty()) {
      throw new EntityNotFoundException("Group with ID " + groupId + " Not Found");
    }

    return GroupTransformer.transformEntityToModel(groupEntityOptional.get());
  }

  public List<Group> getGroupsByUserId(String userId) {
    List<GroupEntity> groupEntities = groupRepository.findAllByOwner_UserId(UUID.fromString(userId));

    return groupEntities.stream()
        .map(GroupTransformer::transformEntityToModel)
        .toList();
  }

  @Transactional
  public Group update(Group group) {

    GroupEntity groupEntity = groupRepository.findById(UUID.fromString(group.getGroupId()))
        .orElseThrow(() -> new EntityNotFoundException("Group With ID " + group.getGroupId() + " Not Found"));

    if (group.getOwner() != null && group.getOwner().getUserId() != null) {
      group.setOwner(userService.getUserByUserId(group.getOwner().getUserId()));
    }

    if (group.getReward() != null && group.getReward().getRewardId() != null) {
      group.setReward(rewardService.getRewardByRewardId(group.getReward().getRewardId()));
    }

    groupEntity.setName(group.getName());
    groupEntity.setReward(RewardTransformer.transformModelToEntity(group.getReward()));
    groupEntity.setOwner(UserTransformer.transformModelToEntity(group.getOwner()));
    groupEntity.setCoin(group.getCoin());

    return GroupTransformer.transformEntityToModel(
        groupRepository.save(groupEntity)
    );
  }

  @Transactional
  public void delete(String groupId) {
    if (!groupRepository.existsById(UUID.fromString(groupId))) {
      throw new EntityNotFoundException("Group with ID " + groupId + " Not Found");
    }

    groupRepository.deleteById(UUID.fromString(groupId));
  }

  public boolean groupExists(String groupId) {
    return groupRepository.existsById(UUID.fromString(groupId));
  }
}
