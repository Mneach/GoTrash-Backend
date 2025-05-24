package com.gotrash.api.v1.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
  private String description;
  private LocalDateTime createdAt;
  private LocalDateTime updatedAt;
}
