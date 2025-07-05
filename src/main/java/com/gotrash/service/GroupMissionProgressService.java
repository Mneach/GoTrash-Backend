package com.gotrash.service;

import com.gotrash.api.v1.model.Citizen;
import com.gotrash.api.v1.model.GroupMemberMissionContribution;
import com.gotrash.api.v1.model.GroupMissionProgress;
import com.gotrash.api.v1.transformer.GroupMissionProgressTransformer;
import com.gotrash.entity.*;
import com.gotrash.exception.rest.BadRequestException;
import com.gotrash.exception.rest.EntityNotFoundException;
import com.gotrash.repository.GroupMissionProgressRepository;
import com.gotrash.repository.GroupRepository;
import com.gotrash.repository.MissionRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class GroupMissionProgressService {

  private final GroupRepository groupRepository;
  private final MissionRepository missionRepository;
  private final GroupMissionProgressRepository groupMissionProgressRepository;
  private final GroupMemberMissionContributionService groupMemberMissionContributionService;

  @Transactional
  public List<GroupMissionProgress> getAllGroupMissionProgressRepository() {

    return groupMissionProgressRepository.findAll()
        .stream()
        .map(GroupMissionProgressTransformer::transformEntityToModel)
        .toList();
  }

  @Transactional
  public GroupMissionProgress save(GroupMissionProgress groupMissionProgress) {

    MissionEntity missionEntity = missionRepository.findById(UUID.fromString(groupMissionProgress.getMission().getMissionId()))
        .orElseThrow(() -> new EntityNotFoundException("Mission with id " + groupMissionProgress.getMission().getMissionId() + " Not Found"));

    GroupEntity groupEntity = groupRepository.findById(UUID.fromString(groupMissionProgress.getGroup().getGroupId()))
        .orElseThrow(() -> new EntityNotFoundException("Mission with id " + groupMissionProgress.getGroup().getGroupId() + " Not Found"));

    GroupMissionProgressEntity groupMissionProgressEntity = GroupMissionProgressTransformer.transformModelToEntity(
        groupMissionProgress
    );

    if (checkActiveGroupMission(groupMissionProgress.getGroup().getGroupId())) {
      throw new BadRequestException("Currently there is active group mission");
    }

    groupMissionProgressEntity.setMission(missionEntity);
    groupMissionProgressEntity.setGroup(groupEntity);

    groupMissionProgressEntity = groupMissionProgressRepository.save(groupMissionProgressEntity);
    String groupMissionProgressId = groupMissionProgressEntity.getGroupMissionProgressId().toString();

    // create group member contribution for current mission
    groupMissionProgressEntity.getGroup().getGroupMembers().forEach(
        groupMemberEntity -> {
          groupMemberMissionContributionService.save(
              GroupMemberMissionContribution.builder()
                  .citizen(Citizen.builder().userId(groupMemberEntity.getCitizen().getUserId().toString()).build())
                  .groupMissionProgress(GroupMissionProgress.builder().groupMissionProgressId(groupMissionProgressId).build())
                  .contribution(BigDecimal.ZERO)
                  .build()
          );
        }
    );

    return GroupMissionProgressTransformer.transformEntityToModel(
        groupMissionProgressEntity
    );
  }

  @Transactional
  public List<GroupMissionProgress> getAllGroupMissionProgressFilterByGroupId(String groupId) {

    return groupMissionProgressRepository.findAllByGroup_GroupId(UUID.fromString(groupId))
        .stream()
        .map(GroupMissionProgressTransformer::transformEntityToModel)
        .toList();
  }

  @Transactional
  public GroupMissionProgress getActiveGroupMissionProgressByGroupId(String groupId) {

    Optional<GroupMissionProgressEntity> groupMissionProgressEntityOptional = groupMissionProgressRepository.findByGroup_GroupIdAndIsRewardClaimed(
        UUID.fromString(groupId), false
    );

    return groupMissionProgressEntityOptional
        .map(GroupMissionProgressTransformer::transformEntityToModel)
        .orElse(null);
  }

  @Transactional
  public void claimGroupMission(String groupMissionProgressId) {

    GroupMissionProgressEntity groupMissionProgressEntity = groupMissionProgressRepository.findById(
        UUID.fromString(groupMissionProgressId)
    ).orElseThrow(() -> new EntityNotFoundException("Group Mission Progress With ID " + groupMissionProgressId + " Not Found"));

    if (groupMissionProgressEntity.getCurrentProgress().compareTo(groupMissionProgressEntity.getMission().getTargetValue()) < 0) {
      // Progress hasn't reached target
      throw new BadRequestException("Current Progress hasn't reached target");
    }

    // validate if group mission reward already claimed
    if (groupMissionProgressEntity.getIsRewardClaimed()) {
      throw new BadRequestException("Your already claimed the reward");
    }

    // distribute coins and ratings
    groupMissionProgressEntity.getGroup().getGroupMembers().forEach(
        groupMemberEntity -> {

          // give citizen coin
          groupMemberEntity.getCitizen().setCoin(
              groupMemberEntity.getCitizen().getCoin().add(
                  groupMissionProgressEntity.getMission().getRewardCoins()
              )
          );

          // give citizen rating
          groupMemberEntity.getCitizen().setRating(
              groupMemberEntity.getCitizen().getRating().add(
                  groupMissionProgressEntity.getMission().getRewardRatings()
              )
          );
        }
    );

    // claim reward
    groupMissionProgressEntity.setIsRewardClaimed(true);

    groupMissionProgressRepository.save(groupMissionProgressEntity);
  }

  public void updateGroupMissionProgress(String citizenId, String trashId, BigDecimal trashWeight) {

    List<GroupEntity> groupEntities = groupRepository.findGroupsByUserId(UUID.fromString(citizenId));

    // Looping for every group in user service
    groupEntities.forEach(
        groupEntity -> {

          // Get group active mission
          Optional<GroupMissionProgressEntity> groupMissionProgressEntityOptional = groupMissionProgressRepository.findByGroup_GroupIdAndIsRewardClaimed(
              groupEntity.getGroupId(), false
          );

          if (groupMissionProgressEntityOptional.isPresent()) {
            GroupMissionProgressEntity groupMissionProgressEntity = groupMissionProgressEntityOptional.get();
            MissionEntity missionEntity = groupMissionProgressEntity.getMission();

            BigDecimal contribution = BigDecimal.valueOf(1);
            if (missionEntity.getTrash() != null && missionEntity.getTrash().getTrashId() != null) {
              // Update based on the trash
              if (missionEntity.getTrash().toString().equals(trashId)) {

                if (missionEntity.getGoalType().toLowerCase().contains("weight")) {
                  handleWeightMission(groupMissionProgressEntity, trashWeight);
                  contribution = trashWeight;
                } else {
                  handleNonWeightMission(groupMissionProgressEntity);
                }

              }
            } else if (missionEntity.getGoalType().toLowerCase().contains("weight")) {
              handleWeightMission(groupMissionProgressEntity, trashWeight);
              contribution = trashWeight;
            }else {
              handleNonWeightMission(groupMissionProgressEntity);
            }

            if (groupMissionProgressEntity.getCurrentProgress().compareTo(groupMissionProgressEntity.getMission().getTargetValue()) > 0) {
              // Group has been reached the target
              groupMissionProgressEntity.setCurrentProgress(groupMissionProgressEntity.getMission().getTargetValue());
            }

            groupMissionProgressRepository.save(groupMissionProgressEntity);

            // Update Group Member Mission Contribution
            groupMemberMissionContributionService.updateMemberContribution(
                GroupMemberMissionContribution.builder()
                    .citizen(Citizen.builder().userId(citizenId).build())
                    .groupMissionProgress(GroupMissionProgress.builder().groupMissionProgressId(groupMissionProgressEntity.getGroupMissionProgressId().toString()).build())
                    .contribution(contribution)
                    .build()
            );
          }
        }
    );
  }

  public boolean checkActiveGroupMission(String groupId) {
    return getActiveGroupMissionProgressByGroupId(groupId) != null;
  }

  private void handleNonWeightMission(GroupMissionProgressEntity groupMissionProgressEntity) {
    groupMissionProgressEntity.setCurrentProgress(
        groupMissionProgressEntity.getCurrentProgress().add(BigDecimal.valueOf(1))
    );
  }

  private void handleWeightMission(GroupMissionProgressEntity groupMissionProgressEntity, BigDecimal trashWeight) {
    groupMissionProgressEntity.setCurrentProgress(
        groupMissionProgressEntity.getCurrentProgress().add(trashWeight)
    );
  }
}
