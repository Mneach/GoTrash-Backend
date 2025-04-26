package com.gotrash.api.v1.model;

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
public class Company {
  private String userId;
  private User user;
  private String email;
  private String password;
  private UserRole role;
  private String name;
  private String address;
  private LocalDateTime createdAt;
  private LocalDateTime updatedAt;
  private Long version;
}
