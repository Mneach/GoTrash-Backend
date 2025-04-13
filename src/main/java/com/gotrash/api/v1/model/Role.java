package com.gotrash.api.v1.model;

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
public class Role {
  private String roleId;
  private RoleName name;
  private LocalDateTime createdAt;
  private LocalDateTime updatedAt;
}
