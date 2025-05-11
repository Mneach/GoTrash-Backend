package com.gotrash.repository;

import com.gotrash.entity.WasteBankWarehouseEntity;
import com.gotrash.entity.id.WasteBankWarehouseId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface WasteBankWarehouseRepository extends JpaRepository<WasteBankWarehouseEntity, WasteBankWarehouseId> {

  @Query("SELECT w FROM WasteBankWarehouse w WHERE w.id.wasteBankId = :wasteBankId")
  List<WasteBankWarehouseEntity> findAllByWasteBankId(@Param("wasteBankId") UUID wasteBankId);
}
