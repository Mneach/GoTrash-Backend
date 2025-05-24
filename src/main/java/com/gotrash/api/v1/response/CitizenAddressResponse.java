package com.gotrash.api.v1.response;

import com.gotrash.api.v1.model.Citizen;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CitizenAddressResponse {
  private String citizenAddressId;
  private String label;
  private String address;
  private String note;
  private LocalDateTime createdAt;
  private LocalDateTime updatedAt;
}
