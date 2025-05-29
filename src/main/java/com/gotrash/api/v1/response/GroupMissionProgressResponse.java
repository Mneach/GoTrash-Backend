package com.gotrash.api.v1.response;

import com.gotrash.api.v1.model.GroupMemberMissionContribution;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GroupMissionProgressResponse {
  private String groupMissionProgressId;
  private MissionResponse mission;
  private List<GroupMemberMissionContributionResponse> groupMemberMissionContributions;
  private BigDecimal currentProgress;
  private Boolean isRewardClaimed;
  private LocalDateTime createdAt;
  private LocalDateTime updatedAt;
}
