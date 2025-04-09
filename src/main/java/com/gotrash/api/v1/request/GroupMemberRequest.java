package com.gotrash.api.v1.request;

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
public class GroupMemberRequest {
  private String groupMemberId;
  private String userId;
  private String groupId;
}
