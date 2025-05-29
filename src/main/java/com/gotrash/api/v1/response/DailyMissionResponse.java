package com.gotrash.api.v1.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DailyMissionResponse {
  private String dailyMissionProgressId;
  private MissionResponse mission;
  private CitizenResponse citizen;
  private LocalDate activeDate;
  private BigDecimal currentProgress;
  private Boolean isRewardClaimed;
  private LocalDateTime createdAt;
  private LocalDateTime updatedAt;
}
