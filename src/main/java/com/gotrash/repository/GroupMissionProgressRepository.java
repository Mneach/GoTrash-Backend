package com.gotrash.repository;

import com.gotrash.entity.GroupMissionProgressEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface GroupMissionProgressRepository extends JpaRepository<GroupMissionProgressEntity, UUID> {

  List<GroupMissionProgressEntity> findAllByGroup_GroupId(UUID groupId);

  Optional<GroupMissionProgressEntity> findByGroup_GroupIdAndIsRewardClaimed(UUID groupId, boolean isRewardClaimed);

  List<GroupMissionProgressEntity> findAllByGroup_GroupIdAndIsRewardClaimed(UUID groupId, boolean isRewardClaimed);

}
