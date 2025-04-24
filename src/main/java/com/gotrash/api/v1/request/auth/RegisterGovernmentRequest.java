package com.gotrash.api.v1.request.auth;

import com.gotrash.api.v1.model.User;
import com.gotrash.api.v1.request.CitizenRequest;
import com.gotrash.api.v1.request.GovernmentRequest;
import com.gotrash.api.v1.request.UserRequest;
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
public class RegisterGovernmentRequest {
  private String email;
  private String password;
  private UserRole role;
  private String name;
}
