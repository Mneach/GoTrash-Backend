package com.gotrash.repository;

import com.gotrash.entity.ShipmentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ShipmentRepository extends JpaRepository<ShipmentEntity, UUID> {
  List<ShipmentEntity> findAllByCitizen_UserId(UUID userId);
}
