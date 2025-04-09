package com.gotrash.api.v1.response;

import com.gotrash.api.v1.model.Group;
import com.gotrash.api.v1.model.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GroupMemberResponse {
  private String groupMemberId;
  private User user;
  private LocalDateTime createdAt;
  private LocalDateTime updatedAt;
}
