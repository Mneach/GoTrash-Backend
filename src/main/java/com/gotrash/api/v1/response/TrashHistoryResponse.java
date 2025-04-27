package com.gotrash.api.v1.response;

import com.gotrash.api.v1.model.Trash;
import com.gotrash.api.v1.model.User;
import com.gotrash.api.v1.request.TrashBinRequest;
import com.gotrash.entity.UserEntity;
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
public class TrashHistoryResponse {
  private String trashHistoryId;
  private UserResponse citizen;
  private TrashResponse trash;
  private TrashBinResponse trashBin;
  private BigDecimal weight;
  private BigInteger totalCoin;
  private LocalDateTime createdAt;
  private LocalDateTime updatedAt;
}
