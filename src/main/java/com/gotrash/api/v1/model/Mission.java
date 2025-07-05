package com.gotrash.api.v1.model;

import com.gotrash.api.v1.model.TrashCategory;
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
public class Mission {
  private String missionId;
  private String type;
  private String goalType;
  private String title;
  private String description;
  private BigDecimal targetValue;
  private Trash trash;
  private BigInteger rewardCoins;
  private BigInteger rewardRatings;
  private LocalDateTime createdAt;
  private LocalDateTime updatedAt;
}
