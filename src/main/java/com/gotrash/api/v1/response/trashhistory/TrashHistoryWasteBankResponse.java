package com.gotrash.api.v1.response.trashhistory;

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
public class TrashHistoryWasteBankResponse {
  private String trashHistoryId;
  private CitizenResponse citizen;
  private TrashResponse trash;
  private TrashBinResponse trashBin;
  private BigDecimal weight;
  private BigInteger totalCoin;
  private BigInteger bleId;
  private LocalDateTime createdAt;
  private LocalDateTime updatedAt;
}
