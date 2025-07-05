package com.gotrash.api.v1.response;

import com.gotrash.api.v1.model.TrashCategory;
import com.gotrash.api.v1.response.TrashCategoryResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MissionResponse {
  private String missionId;
  private String type;
  private String goalType;
  private String title;
  private String description;
  private BigDecimal targetValue;
  private TrashResponse trash;
  private BigInteger rewardCoins;
  private BigInteger rewardRatings;
  private LocalDateTime createdAt;
  private LocalDateTime updatedAt;
}
