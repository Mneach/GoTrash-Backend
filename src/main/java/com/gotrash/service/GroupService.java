package com.gotrash.service;

import com.gotrash.api.v1.model.*;
import com.gotrash.api.v1.transformer.*;
import com.gotrash.entity.*;
import com.gotrash.exception.rest.BadRequestException;
import com.gotrash.repository.CitizenRepository;
import com.gotrash.repository.GroupMemberRepository;
import com.gotrash.repository.GroupRepository;
import com.gotrash.repository.RewardRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import javax.swing.text.html.Option;

@Service
@RequiredArgsConstructor
public class GroupService {

  private final GroupRepository groupRepository;
  private final GroupMemberService groupMemberService;
  private final GroupMemberRepository groupMemberRepository;
  private final CitizenService citizenService;
  private final CitizenRepository citizenRepository;
  private final GroupMissionProgressService groupMissionProgressService;
  private final GroupMemberMissionContributionService groupMemberMissionContributionService;

  @Transactional
  public Group save(Group group) {
    CitizenEntity citizenEntity = citizenRepository.findById(UUID.fromString(group.getOwner().getUserId()))
        .orElseThrow(() -> new RuntimeException("Citizen not found"));

    // Build GroupEntity manually
    GroupEntity groupEntity = new GroupEntity();
    groupEntity.setName(group.getName());
    groupEntity.setOwner(citizenEntity);

    // Save GroupEntity
    groupEntity = groupRepository.save(groupEntity);

    // Save GroupMemberEntity
    GroupMemberEntity groupMemberEntity = new GroupMemberEntity();
    groupMemberEntity.setCitizen(citizenEntity);
    groupMemberEntity.setGroup(groupEntity);
    groupMemberRepository.save(groupMemberEntity);

    Group resultGroup = GroupTransformer.transformEntityToModel(groupEntity);
    resultGroup.setGroupMembers(List.of(GroupMemberTransformer.transformEntityToModel(groupMemberEntity)));

    return resultGroup;
  }


  @Transactional
  public void addMember(GroupMember groupMember) {

    GroupEntity groupEntity = groupRepository.findById(UUID.fromString(groupMember.getGroup().getGroupId()))
        .orElseThrow(() -> new EntityNotFoundException("Group not found"));

    CitizenEntity citizenEntity = citizenRepository.findById(UUID.fromString(groupMember.getCitizen().getUserId()))
        .orElseThrow(() -> new RuntimeException("Citizen not found"));

    boolean isAlreadyMember = groupEntity.getGroupMembers().stream()
        .anyMatch(groupMemberEntity -> groupMemberEntity.getCitizen().getUserId().equals(UUID.fromString(groupMember.getCitizen().getUserId())));;

    if (isAlreadyMember) {
      throw new BadRequestException("User is already a member of the group.");
    }

    // Save GroupMemberEntity
    GroupMemberEntity groupMemberEntity = new GroupMemberEntity();
    groupMemberEntity.setCitizen(citizenEntity);
    groupMemberEntity.setGroup(groupEntity);
    groupMemberEntity = groupMemberRepository.save(groupMemberEntity);

    String groupId = groupMemberEntity.getGroup().getGroupId().toString();

    GroupMissionProgress groupMissionProgress = groupMissionProgressService.getActiveGroupMissionProgressByGroupId(groupId);

    // if there are active group missions
    if (groupMissionProgress != null) {
      String citizenId = citizenEntity.getUserId().toString();
      String groupMissionProgressId = groupMissionProgress.getGroupMissionProgressId();

      // If the user never contribute to the current active group mission,
      // then add user to current active group mission contribution.
      if (!groupMemberMissionContributionService.checkGroupMemberContribution(citizenId, groupMissionProgressId)) {
        groupMemberMissionContributionService.save(
            GroupMemberMissionContribution.builder()
                .citizen(Citizen.builder().userId(citizenId).build())
                .groupMissionProgress(groupMissionProgress)
                .contribution(BigDecimal.ZERO)
                .build()
        );
      }

    }

  }

  @Transactional
  public void removeMember(GroupMember groupMember) {
    GroupEntity groupEntity = groupRepository.findById(UUID.fromString(groupMember.getGroup().getGroupId()))
        .orElseThrow(() -> new EntityNotFoundException("Group not found"));
    Citizen citizen = citizenService.getCitizenByUserId(groupMember.getCitizen().getUserId());

    GroupMemberEntity memberToRemove = groupEntity.getGroupMembers().stream()
        .filter(member -> member.getCitizen().getUserId().equals(UUID.fromString(citizen.getUserId())))
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

  public List<Group> getGroupsFilterByUserId(String userId) {
    List<GroupEntity> groupEntities = groupRepository.findGroupsByUserId(UUID.fromString(userId));

    return groupEntities.stream()
        .map(GroupTransformer::transformEntityToModel)
        .toList();
  }

  @Transactional
  public Group update(Group group) {

    GroupEntity groupEntity = groupRepository.findById(UUID.fromString(group.getGroupId()))
        .orElseThrow(() -> new EntityNotFoundException("Group With ID " + group.getGroupId() + " Not Found"));

    CitizenEntity citizenEntity = citizenRepository.findById(UUID.fromString(group.getOwner().getUserId()))
        .orElseThrow(() -> new RuntimeException("Citizen not found"));

    groupEntity.setName(group.getName());
    groupEntity.setOwner(citizenEntity);

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
