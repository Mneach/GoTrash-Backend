package com.gotrash.api.v1.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ShipmentResponse {
  private String shipmentId;
  private CitizenResponse citizen;
  private CitizenAddressResponse citizenAddress;
  private RewardResponse reward;
  private String status;
  private String description;
  private LocalDateTime createdAt;
  private LocalDateTime updatedAt;
}
