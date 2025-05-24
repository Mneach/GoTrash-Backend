package com.gotrash.api.v1.response.pendingtrashhistory;

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
public class ClaimPendingTrashHistoryResponse {
  private BigInteger totalCoin;
  private BigDecimal totalWeight;
  private BigInteger totalRating;
}
