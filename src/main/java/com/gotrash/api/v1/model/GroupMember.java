package com.gotrash.api.v1.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GroupMember {
  private String groupMemberId;
  private User user;
  private Group group;
  private LocalDateTime createdAt;
  private LocalDateTime updatedAt;
}
