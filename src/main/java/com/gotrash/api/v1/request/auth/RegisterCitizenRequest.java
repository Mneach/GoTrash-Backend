package com.gotrash.api.v1.request.auth;

import com.gotrash.api.v1.request.CitizenRequest;
import com.gotrash.api.v1.request.UserRequest;
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
public class RegisterCitizenRequest {
  private String email;
  private String password;
  private UserRole role;
  private String name;
  private String phoneNumber;
  private String imageName;
  private String imageUrl;
  private BigInteger coin;
  private BigInteger rating;
}
