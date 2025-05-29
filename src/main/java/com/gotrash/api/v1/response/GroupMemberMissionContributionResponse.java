package com.gotrash.api.v1.response;

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
public class GroupMemberMissionContributionResponse {
  private String contributionId;
  private CitizenResponse citizen;
  private BigDecimal contribution;
  private LocalDateTime createdAt;
  private LocalDateTime updatedAt;
}
