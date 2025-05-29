package com.gotrash.api.v1.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GroupMissionContribution {
  private String contributionId;
  private GroupMissionProgress groupMissionProgress;
  private Citizen citizen;
  private BigDecimal contribution;
  private LocalDateTime createdAt;
  private LocalDateTime updatedAt;
}
