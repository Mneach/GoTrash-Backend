package com.gotrash.repository;

import com.gotrash.api.v1.model.dashboard.WasteBankMoneySummary;
import com.gotrash.api.v1.model.dashboard.WasteBankTrashCategorySummary;
import com.gotrash.api.v1.model.dashboard.WasteBankTrashSummary;
import com.gotrash.entity.WasteBankEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface WasteBankRepository extends JpaRepository<WasteBankEntity, UUID> {
  Optional<WasteBankEntity> findByUser_UserId(UUID userId);


  @Query(
      value = """
            SELECT      
                  wb.user_id AS wasteBankId,
                  wb.name AS wasteBankName,
                  TRUNC(SUM(price), 3) as money 
            FROM gotrash.waste_banks AS wb
            INNER JOIN gotrash.shipments AS sm ON wb.user_id = sm.waste_bank_user_id
            WHERE 
              wb.user_id = :wasteBankUserId
              AND
              sm.status = :shipmentStatus
            GROUP BY wb.user_id, wb.name
        """,
      nativeQuery = true
  )
  WasteBankMoneySummary sumWasteBankMoneyByWasteBankId(
      @Param("wasteBankUserId") UUID wasteBankUserId,
      @Param("shipmentStatus") String shipmentStatus);


  @Query(
      value = """
            SELECT      
                  wb.user_id AS wasteBankId,
                  wb.name AS wasteBankName,
                  TRUNC(SUM(weight), 2) as totalWeight 
            FROM gotrash.waste_banks AS wb
            INNER JOIN gotrash.trash_bins AS tb ON wb.user_id = tb.waste_bank_id
            INNER JOIN gotrash.trash_histories AS th ON th.trash_bin_id = tb.trash_bin_id
            WHERE wb.user_id = :wasteBankUserId
            GROUP BY wb.user_id, wb.name
        """,
      nativeQuery = true
  )
  WasteBankTrashSummary sumTrashWeightByWasteBankId(@Param("wasteBankUserId") UUID wasteBankUserId);


  @Query(
      value = """
            SELECT 
                  wb.user_id AS wasteBankId,
                  wb.name AS wasteBankName,
                  TRUNC(SUM(weight), 2) as totalWeight
            FROM gotrash.waste_banks AS wb
            INNER JOIN gotrash.trash_bins AS tb ON wb.user_id = tb.waste_bank_id
            INNER JOIN gotrash.trash_histories AS th ON th.trash_bin_id = tb.trash_bin_id
            GROUP BY wb.user_id, wb.name
        """,
      nativeQuery = true
  )
  List<WasteBankTrashSummary> sumTrashWeightByGroupByWasteBankId();

  @Query(value = """
        SELECT 
            wb.user_id AS wasteBankId,
            wb.name AS wasteBankName,
            tc.name AS trashCategory,
            TRUNC(SUM(weight), 2) as totalWeight
        FROM gotrash.waste_banks wb
        INNER JOIN gotrash.trash_bins tb ON wb.user_id = tb.waste_bank_id
        INNER JOIN gotrash.trash_histories th ON th.trash_bin_id = tb.trash_bin_id
        INNER JOIN gotrash.trashes t ON t.trash_id = th.trash_id
        INNER JOIN gotrash.trash_categories tc ON tc.trash_category_id = t.trash_category_id
        WHERE wb.user_id = :wasteBankUserId
        GROUP BY wb.user_id, wb.name, tc.name
    """, nativeQuery = true)
  List<WasteBankTrashCategorySummary> sumTrashWeightByWasteBankIdGroupedByCategory(@Param("wasteBankUserId") UUID wasteBankUserId);
}
