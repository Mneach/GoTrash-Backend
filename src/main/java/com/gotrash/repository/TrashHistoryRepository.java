package com.gotrash.repository;

import com.gotrash.entity.NotificationEntity;
import com.gotrash.entity.TrashHistoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Repository
public interface TrashHistoryRepository extends JpaRepository<TrashHistoryEntity, UUID> {
    List<TrashHistoryEntity> findAllByUser_UserId(UUID userId);
    List<TrashHistoryEntity> findAllByUser_UserIdAndCreatedAtBetween(UUID citizenId, LocalDateTime startDate, LocalDateTime endDate);

    @Query("SELECT th FROM TrashHistoryEntity th WHERE th.trashBin.wasteBank.userId = :wasteBankId")
    List<TrashHistoryEntity> findAllByWasteBankId(@Param("wasteBankId") UUID wasteBankId);
}
