package com.gotrash.api.v1.response;

import com.gotrash.constant.UserRole;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigInteger;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserResponse {
  private String userId;
  private String email;
  private UserRole role;
  private LocalDateTime createdAt;
  private LocalDateTime updatedAt;
}
