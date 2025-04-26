package com.gotrash.api.v1.request;


import com.gotrash.constant.UserRole;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GovernmentRequest {
  private String email;
  private UserRole role;
  private String name;
  private String password;
  private LocalDateTime createdAt;
  private LocalDateTime updatedAt;
}
