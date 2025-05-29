package com.gotrash.repository;

import com.gotrash.entity.DailyMissionProgressEntity;
import com.gotrash.entity.MissionEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface MissionRepository extends JpaRepository<MissionEntity, UUID> {

  List<MissionEntity> findAllByGoalType(String goalType);
  List<MissionEntity> findAllByType(String type);
}
