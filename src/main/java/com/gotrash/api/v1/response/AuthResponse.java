package com.gotrash.api.v1.response;

import com.gotrash.constant.UserRole;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AuthResponse {
  private String token;
  private UserRole role;
}
