package com.gotrash.repository;

import com.gotrash.entity.ShipmentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface ShipmentRepository extends JpaRepository<ShipmentEntity, UUID> {

  List<ShipmentEntity> findAllByDestinationCompany_UserId(UUID companyId);
  List<ShipmentEntity> findAllByWasteBank_UserId(UUID wasteBankId);

}
