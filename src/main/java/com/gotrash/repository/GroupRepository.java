package com.gotrash.repository;

import com.gotrash.entity.GroupEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface GroupRepository extends JpaRepository<GroupEntity, UUID> {

  @Query("SELECT g FROM GroupEntity g " +
      "JOIN FETCH g.owner " +
      "JOIN FETCH g.reward " +
      "LEFT JOIN FETCH g.groupMembers " +
      "WHERE g.groupId = :groupId")
  Optional<GroupEntity> findByIdWithAllRelations(@Param("groupId") UUID groupId);
}
