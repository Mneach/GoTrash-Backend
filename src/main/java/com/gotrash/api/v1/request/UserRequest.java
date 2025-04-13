package com.gotrash.api.v1.request;

import com.gotrash.api.v1.model.Role;
import com.gotrash.constant.RoleName;
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
  private String userId;
  private String username;
  private String password;
  private String email;
  private String phoneNumber;
  private String imageUrl;
  private BigInteger coin;
  private RoleName role;
}
