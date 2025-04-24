package com.gotrash.repository;

import com.gotrash.api.v1.model.dashboard.WasteBankTrashCategorySummary;
import com.gotrash.api.v1.model.dashboard.WasteBankTrashSummary;
import com.gotrash.entity.WasteBankEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface WasteBankRepository extends JpaRepository<WasteBankEntity, UUID> {
  Optional<WasteBankEntity> findByUser_UserId(UUID userId);

  @Query(
      value = """
            SELECT      
                  wb.user_id AS wasteBankId,
                  wb.name AS wasteBankName,
                  COUNT(*) as totalTrash
            FROM gotrash.waste_banks AS wb
            INNER JOIN gotrash.trash_bins AS tb ON wb.user_id = tb.waste_bank_id
            INNER JOIN gotrash.trash_histories AS th ON th.trash_bin_id = tb.trash_bin_id
            WHERE wb.user_id = :wasteBankUserId
            GROUP BY wb.user_id, wb.name
        """,
      nativeQuery = true
  )
  WasteBankTrashSummary countTotalTrashByWasteBankId(@Param("wasteBankUserId") UUID wasteBankUserId);


  @Query(
      value = """
            SELECT 
                  wb.user_id AS wasteBankId,
                  wb.name AS wasteBankName,
                  COUNT(*) as totalTrash
            FROM gotrash.waste_banks AS wb
            INNER JOIN gotrash.trash_bins AS tb ON wb.user_id = tb.waste_bank_id
            INNER JOIN gotrash.trash_histories AS th ON th.trash_bin_id = tb.trash_bin_id
            GROUP BY wb.user_id, wb.name
        """,
      nativeQuery = true
  )
  List<WasteBankTrashSummary> countTotalTrashByGroupByWasteBankId();

  @Query(value = """
        SELECT 
            wb.user_id AS wasteBankId,
            wb.name AS wasteBankName,
            tc.name AS trashCategory,
            COUNT(*) AS totalTrash
        FROM gotrash.waste_banks wb
        INNER JOIN gotrash.trash_bins tb ON wb.user_id = tb.waste_bank_id
        INNER JOIN gotrash.trash_histories th ON th.trash_bin_id = tb.trash_bin_id
        INNER JOIN gotrash.trashes t ON t.trash_id = th.trash_id
        INNER JOIN gotrash.trash_categories tc ON tc.trash_category_id = t.trash_category_id
        WHERE wb.user_id = :wasteBankUserId
        GROUP BY wb.user_id, wb.name, tc.name
    """, nativeQuery = true)
  List<WasteBankTrashCategorySummary> countTotalTrashByWasteBankIdGroupedByCategory(@Param("wasteBankUserId") UUID wasteBankUserId);
}
