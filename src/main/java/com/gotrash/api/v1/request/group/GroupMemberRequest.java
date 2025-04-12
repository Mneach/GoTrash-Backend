package com.gotrash.api.v1.request.group;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GroupMemberRequest {
  private String groupMemberId;
  private String userId;
  private String groupId;
}
