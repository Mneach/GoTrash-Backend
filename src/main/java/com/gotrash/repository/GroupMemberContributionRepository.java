package com.gotrash.repository;

import com.gotrash.entity.GroupMemberEntity;
import com.gotrash.entity.GroupMemberMissionContributionEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface GroupMemberContributionRepository extends JpaRepository<GroupMemberMissionContributionEntity, UUID> {

  Optional<GroupMemberMissionContributionEntity> findByCitizen_UserIdAndGroupMissionProgress_GroupMissionProgressId(UUID citzenId, UUID groupMissionProgressId);

  List<GroupMemberMissionContributionEntity>
  findByCitizenUserIdInAndGroupMissionProgressGroupMissionProgressId(
      List<UUID> citizenIds,
      UUID missionProgressId
  );
}
