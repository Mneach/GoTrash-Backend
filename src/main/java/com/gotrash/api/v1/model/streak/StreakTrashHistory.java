package com.gotrash.api.v1.model.streak;

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
public class StreakTrashHistory {
  private String name;
  private String category;
  private BigInteger totalCoin;
  private BigDecimal weight;
}
