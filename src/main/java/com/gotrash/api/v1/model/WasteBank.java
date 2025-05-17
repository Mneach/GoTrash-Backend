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
public class WasteBank {
  private String userId;
  private User user;
  private String email;
  private String password;
  private UserRole role;
  private String name;
  private Double latitude;
  private Double longitude;
  private String address;
  private String imageUrl;
  private String phoneNumber;
  private String operationalHours;
  private LocalDateTime createdAt;
  private LocalDateTime updatedAt;
}
