package com.gotrash.repository;

import com.gotrash.entity.GroupMemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface GroupMemberRepository extends JpaRepository<GroupMemberEntity, UUID> {
  Optional<GroupMemberEntity> findByUser_UserIdAndGroup_GropuId(UUID userId, UUID groupId);
}
