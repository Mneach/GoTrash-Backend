package com.gotrash.api.v1.response;

import com.gotrash.api.v1.response.GroupResponse;
import com.gotrash.api.v1.response.MissionResponse;
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
public class GroupMissionProgressResponse {
  private String groupMissionProgressId;
  private MissionResponse mission;
  private GroupResponse group;
  private BigDecimal currentProgress;
  private Boolean isRewardClaimed;
  private LocalDateTime createdAt;
  private LocalDateTime updatedAt;
}
