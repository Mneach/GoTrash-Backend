package com.gotrash.repository;

import com.gotrash.entity.GroupEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface GroupRepository extends JpaRepository<GroupEntity, UUID> {

  List<GroupEntity> findAllByOwner_UserId(UUID userId);

  @Query("SELECT gm.group FROM GroupMemberEntity gm WHERE gm.user.userId = :userId")
  List<GroupEntity> findGroupsByUserId(UUID userId);
}
