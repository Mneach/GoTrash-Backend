package com.gotrash.api.v1.request.auth;

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
public class RegisterCompanyRequest {
  private String email;
  private String password;
  private UserRole role;
  private String name;
  private String address;
  private BigInteger coin;
}
