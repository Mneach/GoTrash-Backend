package com.gotrash.api.v1.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigInteger;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Shipment {
  private String shipmentId;
  private Citizen citizen;
  private CitizenAddress citizenAddress;
  private Reward reward;
  private String status;
  private Integer quantity;
  private BigInteger totalCoinUsed;
  private LocalDateTime createdAt;
  private LocalDateTime updatedAt;
}
