package com.gotrash.repository;

import com.gotrash.entity.GovernmentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface GovernmentRepository extends JpaRepository<GovernmentEntity, UUID> {
  Optional<GovernmentEntity> findByUser_UserId(UUID userId);
}
