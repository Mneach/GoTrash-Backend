package com.gotrash.api.v1.response;

import com.gotrash.constant.UserRole;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.gotrash.api.v1.response.TrashHistoryResponse;
import com.gotrash.api.v1.response.GroupResponse;

import java.math.BigInteger;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CitizenResponse {
  private String userId;
  private BigInteger bleId;
  private String email;
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
  private List<TrashHistoryResponse> trashHistories;
  private List<GroupResponse> groups;
}
