package com.gotrash.repository;

import com.gotrash.api.v1.model.dashboard.WasteBankMoneySummary;
import com.gotrash.api.v1.model.dashboard.WasteBankTrashSummary;
import com.gotrash.entity.ShipmentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface ShipmentRepository extends JpaRepository<ShipmentEntity, UUID> {

  List<ShipmentEntity> findAllByDestinationCompany_UserId(UUID companyId);
  List<ShipmentEntity> findAllByWasteBank_UserId(UUID wasteBankId);

  @Query(
      value = """
            SELECT      
                  wb.user_id AS wasteBankId,
                  wb.name AS wasteBankName,
                  TRUNC(SUM(price), 3) as money 
            FROM gotrash.shipments AS sm
            INNER JOIN gotrash.waste_banks AS wb ON wb.user_id = sm.waste_bank_id
            WHERE wb.user_id = :wasteBankUserId
            GROUP BY wb.user_id, wb.name
        """,
      nativeQuery = true
  )
  WasteBankMoneySummary sumWasteBankMoneyByWasteBankId(@Param("wasteBankUserId") UUID wasteBankUserId);

}
