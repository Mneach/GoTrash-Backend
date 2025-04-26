package com.gotrash.api.v1.request.auth;

import com.gotrash.api.v1.request.UserRequest;
import com.gotrash.api.v1.request.WasteBankRequest;
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
public class RegisterWasteBankRequest {
  private String email;
  private String password;
  private UserRole role;
  private String name;
  private Double latitude;
  private Double longitude;
  private String address;
  private String imageName;
  private String imageUrl;
}
