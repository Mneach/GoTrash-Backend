package com.gotrash.api.v1.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PendingTrashHistoryRequest {
  private String trashName;
  private String trashBinId;
  private BigDecimal weight;
}
