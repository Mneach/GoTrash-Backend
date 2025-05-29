package com.gotrash.api.v1.model;

import com.gotrash.api.v1.model.Group;
import com.gotrash.api.v1.model.Mission;
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
public class GroupMissionProgress {
  private String groupMissionProgressId;
  private Mission mission;
  private Group group;
  private BigDecimal currentProgress;
  private Boolean isRewardClaimed;
  private LocalDateTime createdAt;
  private LocalDateTime updatedAt;
}
