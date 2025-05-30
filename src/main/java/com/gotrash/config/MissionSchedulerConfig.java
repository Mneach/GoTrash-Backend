package com.gotrash.config;

import com.gotrash.service.DailyMissionProgressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@Configuration
@EnableScheduling
public class MissionSchedulerConfig {

  @Autowired
  private DailyMissionProgressService dailyMissionProgressService;

  @Scheduled(cron = "0 0 0 * * ?") // Midnight daily
  public void runDailyAssignment() {
    dailyMissionProgressService.assignDailyMissionsToAllCitizens();
  }
}
