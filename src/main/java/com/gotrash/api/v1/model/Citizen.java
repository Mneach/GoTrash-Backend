package com.gotrash.api.v1.model;

import com.gotrash.constant.UserRole;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigInteger;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Citizen {
  private String userId;
  private User user;
  private BigInteger bleId;
  private String email;
  private String password;
  private UserRole role;
  private String name;
  private String phoneNumber;
  private String imageUrl;
  private BigInteger coin;
  private BigInteger rating;
  private Integer currentStreak;
  private Integer longestStreak;
  private LocalDate lastTrashDate;
  private LocalDateTime createdAt;
  private LocalDateTime updatedAt;
}
