package com.gotrash.api.v1.model;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserGroup {
  private String userGroupId;
  private Citizen citizen;
  private List<Group> groupEntities;
  private LocalDateTime createdAt;
  private LocalDateTime updatedAt;
}
