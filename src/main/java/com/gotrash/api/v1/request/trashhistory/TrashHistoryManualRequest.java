package com.gotrash.api.v1.request.trashhistory;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TrashHistoryManualRequest {
  private String phoneNumber;
  private String trashId;
  private String trashBinId;
  private BigDecimal weight;
}
