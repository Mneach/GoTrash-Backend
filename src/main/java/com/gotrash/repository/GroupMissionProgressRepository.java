package com.gotrash.repository;

import com.gotrash.entity.GroupMemberMissionContributionEntity;
import com.gotrash.entity.GroupMissionProgressEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface GroupMissionProgressRepository extends JpaRepository<GroupMissionProgressEntity, UUID> {

}
