package com.gotrash.api.v1.response.pendingtrashhistory;

import com.gotrash.api.v1.response.CitizenResponse;
import com.gotrash.api.v1.response.TrashBinResponse;
import com.gotrash.api.v1.response.TrashResponse;
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
public class PendingTrashHistoryResponse {
  private String pendingTrashHistoryId;
  private TrashResponse trash;
  private TrashBinResponse trashBin;
  private BigDecimal weight;
  private BigInteger totalCoin;
  private LocalDateTime createdAt;
  private LocalDateTime updatedAt;
}
