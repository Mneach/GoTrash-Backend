package com.gotrash.repository;

import com.gotrash.entity.GroupMemberEntity;
import com.gotrash.entity.GroupMemberMissionContributionEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface GroupMemberContributionRepository extends JpaRepository<GroupMemberMissionContributionEntity, UUID> {

}
