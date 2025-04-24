package com.gotrash.api.v1.request;

import com.gotrash.constant.UserRole;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigInteger;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserRequest {
  private String email;
  private String password;
  private UserRole role;
}
