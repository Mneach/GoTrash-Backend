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
public class CitizenResponse {
  private String userId;
  private String email;
  private UserRole role;
  private String name;
  private String phoneNumber;
  private String imageName;
  private String imageUrl;
  private BigInteger coin;
  private LocalDateTime createdAt;
  private LocalDateTime updatedAt;
}
