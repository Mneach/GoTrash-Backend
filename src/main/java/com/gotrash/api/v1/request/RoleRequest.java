package com.gotrash.api.v1.request;

import com.gotrash.constant.RoleName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RoleRequest {
  private String roleId;
  private RoleName roleName;
}
