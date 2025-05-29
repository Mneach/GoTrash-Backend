package com.gotrash.repository;

import com.gotrash.entity.CitizenEntity;
import com.gotrash.entity.DailyMissionProgressEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public interface DailyMissionProgressRepository extends JpaRepository<DailyMissionProgressEntity, UUID> {

  List<DailyMissionProgressEntity> findAllByCitizen_UserId(UUID citizenId);

  @Query("SELECT d FROM DailyMissionProgressEntity d WHERE d.citizen.id = :citizenId AND d.activeDate = :activeDate")
  List<DailyMissionProgressEntity> findByCitizenIdAndActiveDate(
      @Param("citizenId") UUID citizenId,
      @Param("activeDate") LocalDate activeDate);

  @Query("SELECT c FROM CitizenEntity c WHERE NOT EXISTS " +
      "(SELECT 1 FROM DailyMissionProgressEntity d WHERE d.citizen = c AND d.activeDate = :today)")
  List<CitizenEntity> findCitizensWithoutMissionsToday(@Param("today") LocalDate today);
}
