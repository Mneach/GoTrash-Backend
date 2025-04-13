package com.gotrash.api.v1.response;


import com.gotrash.constant.RoleName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RoleResponse {
  private String roleId;
  private RoleName name;
  private LocalDateTime createdAt;
  private LocalDateTime updatedAt;
}
