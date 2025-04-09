package com.gotrash.service;

import com.gotrash.api.v1.model.Group;
import com.gotrash.api.v1.model.GroupMember;
import com.gotrash.api.v1.model.Reward;
import com.gotrash.api.v1.model.User;
import com.gotrash.api.v1.transformer.GroupTransformer;
import com.gotrash.entity.GroupEntity;
import com.gotrash.repository.GroupRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class GroupService {

  private final GroupRepository groupRepository;
  private final RewardService rewardService;
  private final UserService userService;

  public Group save(Group group) {
    Reward reward = rewardService.getRewardByRewardId(group.getReward().getRewardId());
    User user = userService.getUserByUserId(group.getOwner().getUserId());

    GroupMember groupMember = GroupMember.builder()
        .user(user)
        .build();
    // TODO : Add group member

    group.setReward(reward);
    group.setOwner(user);

    GroupEntity groupEntity = groupRepository.save(GroupTransformer.transformModelToEntity(group));

    return GroupTransformer.transformEntityToModel(groupEntity);
  }

  public Group getGroupByGroupId(String groupId) {
    Optional<GroupEntity> groupEntityOptional = groupRepository.findById(UUID.fromString(groupId));

    if (groupEntityOptional.isEmpty()) {
      throw new EntityNotFoundException("Group with ID " + groupId + " Not Found");
    }

    return GroupTransformer.transformEntityToModel(groupEntityOptional.get());
  }

  public List<Group> getGroupsByUserId() {

    // TODO : add impl

    return List.of();
  }



  public Group update(Group group) {

    if (!groupRepository.existsById(UUID.fromString(group.getGroupId()))) {
      throw new EntityNotFoundException("Group with ID " + group.getGroupId() + " Not Found");
    }

    Reward reward = rewardService.getRewardByRewardId(group.getReward().getRewardId());
    group.setReward(reward);

    GroupEntity groupEntity = groupRepository.save(GroupTransformer.transformModelToEntity(group));

    return GroupTransformer.transformEntityToModel(groupEntity);
  }

  public void delete(String groupId) {
    if (!groupRepository.existsById(UUID.fromString(groupId))) {
      throw new EntityNotFoundException("Group with ID " + groupId + " Not Found");
    }

    groupRepository.deleteById(UUID.fromString(groupId));
  }
}
