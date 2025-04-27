package com.gotrash.api.v1.model;

import com.gotrash.constant.UserRole;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Government {
  private String userId;
  private User user;
  private String email;
  private String password;
  private UserRole role;
  private String name;
  private LocalDateTime createdAt;
  private LocalDateTime updatedAt;
}
