package com.gotrash.service;

import com.gotrash.api.v1.model.GroupMemberMissionContribution;
import com.gotrash.api.v1.transformer.GroupMemberMissionContributionTransformer;
import com.gotrash.entity.CitizenEntity;
import com.gotrash.entity.GroupMemberMissionContributionEntity;
import com.gotrash.entity.GroupMissionProgressEntity;
import com.gotrash.exception.rest.EntityNotFoundException;
import com.gotrash.repository.CitizenRepository;
import com.gotrash.repository.GroupMemberContributionRepository;
import com.gotrash.repository.GroupMissionProgressRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class GroupMemberMissionContributionService {

  private final GroupMemberContributionRepository groupMemberContributionRepository;
  private final GroupMissionProgressRepository groupMissionProgressRepository;
  private final CitizenRepository citizenRepository;

  @Transactional
  public void save(GroupMemberMissionContribution groupMemberMissionContribution) {

    UUID groupMissionProgressId = UUID.fromString(groupMemberMissionContribution.getGroupMissionProgress().getGroupMissionProgressId());
    UUID citizenId = UUID.fromString(groupMemberMissionContribution.getCitizen().getUserId());

    GroupMissionProgressEntity groupMissionProgressEntity = groupMissionProgressRepository.findById(groupMissionProgressId)
        .orElseThrow(() -> new EntityNotFoundException("Group Mission Progress With ID " + groupMissionProgressId + " Not Found"));

    CitizenEntity citizenEntity = citizenRepository.findById(citizenId)
        .orElseThrow(() -> new EntityNotFoundException("Citizen with ID " + citizenId + " not found"));


    GroupMemberMissionContributionEntity groupMemberMissionContributionEntity = GroupMemberMissionContributionTransformer.transformModelToEntity(
        groupMemberMissionContribution
    );

    groupMemberMissionContributionEntity.setGroupMissionProgress(groupMissionProgressEntity);
    groupMemberMissionContributionEntity.setCitizen(citizenEntity);
    groupMemberContributionRepository.save(groupMemberMissionContributionEntity);
  }

  @Transactional
  public boolean checkGroupMemberContribution(String citizenId, String groupMissionProgressId) {
    Optional<GroupMemberMissionContributionEntity> groupMemberMissionContributionEntityOpt = groupMemberContributionRepository.findByCitizen_UserIdAndGroupMissionProgress_GroupMissionProgressId(
        UUID.fromString(citizenId), UUID.fromString(groupMissionProgressId)
    );

    return groupMemberMissionContributionEntityOpt.isPresent();
  }

  @Transactional
  public void updateMemberContribution(GroupMemberMissionContribution groupMemberMissionContribution) {

    UUID groupMissionProgressId = UUID.fromString(groupMemberMissionContribution.getGroupMissionProgress().getGroupMissionProgressId());
    UUID citizenId = UUID.fromString(groupMemberMissionContribution.getCitizen().getUserId());

    GroupMemberMissionContributionEntity groupMemberMissionContributionEntity = groupMemberContributionRepository.findByCitizen_UserIdAndGroupMissionProgress_GroupMissionProgressId(
        citizenId, groupMissionProgressId
    ).orElseThrow(() -> new EntityNotFoundException("Group Member Mission Contribution Not Found"));

    if (
        groupMemberMissionContributionEntity.getGroupMissionProgress().getMission().getTargetValue().compareTo(
            groupMemberMissionContributionEntity.getGroupMissionProgress().getCurrentProgress()
        ) < 0
    ) {
      groupMemberMissionContributionEntity.setContribution(
          groupMemberMissionContributionEntity.getContribution().add(
              groupMemberMissionContribution.getContribution()
          )
      );

      groupMemberContributionRepository.save(
          groupMemberMissionContributionEntity
      );
    }

  }

  @Transactional
  public List<GroupMemberMissionContribution> getAllMemberActivelyContribute(List<String> memberIds,
                                                                             String groupMissionProgressId) {

    // Convert String UUIDs to UUID objects
    List<UUID> citizenUuids = memberIds.stream()
        .map(UUID::fromString)
        .toList();

    UUID missionProgressUuid = UUID.fromString(groupMissionProgressId);

    // Find all contributions matching the criteria
    List<GroupMemberMissionContributionEntity> groupMemberMissionContributionEntities =
        groupMemberContributionRepository.findByCitizenUserIdInAndGroupMissionProgressGroupMissionProgressId(
            citizenUuids,
            missionProgressUuid
        );

    // Transform entities to model objects
    return groupMemberMissionContributionEntities.stream()
        .map(GroupMemberMissionContributionTransformer::transformEntityToModel)
        .toList();
  }
}
