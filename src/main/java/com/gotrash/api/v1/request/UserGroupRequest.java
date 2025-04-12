package com.gotrash.api.v1.request;

import com.gotrash.api.v1.model.Group;
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
public class UserGroupRequest {
  private String userGroupId;
  private String userId;
  private List<Group> groupId;
  private LocalDateTime createdAt;
  private LocalDateTime updatedAt;
}
