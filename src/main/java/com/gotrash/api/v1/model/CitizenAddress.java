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
public class CitizenAddress {
  private String citizenAddressId;
  private Citizen citizen;
  private String label;
  private String address;
  private String note;
  private LocalDateTime createdAt;
  private LocalDateTime updatedAt;
}
