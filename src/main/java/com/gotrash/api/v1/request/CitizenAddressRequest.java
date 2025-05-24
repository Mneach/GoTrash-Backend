package com.gotrash.api.v1.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CitizenAddressRequest {
  private String citizenId;
  private String label;
  private String address;
  private String note;
}
