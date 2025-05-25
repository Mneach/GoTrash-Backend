package com.gotrash.api.v1.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ShipmentRequest {
  private String citizenId;
  private String rewardId;
  private String citizenAddressId;
  private Integer quantity;
  private LocalDateTime createdAt;
  private LocalDateTime updatedAt;
}
