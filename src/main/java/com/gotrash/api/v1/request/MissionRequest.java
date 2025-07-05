package com.gotrash.api.v1.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.math.BigInteger;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MissionRequest {
  private String type;
  private String goalType;
  private String title;
  private String description;
  private BigDecimal targetValue;
  private String trashId;
  private BigInteger rewardCoins;
  private BigInteger rewardRatings;
}
