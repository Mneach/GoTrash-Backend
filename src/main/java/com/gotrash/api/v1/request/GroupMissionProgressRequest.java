package com.gotrash.api.v1.request;

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
public class GroupMissionProgressRequest {
  private String missionId;
  private String groupId;
}
