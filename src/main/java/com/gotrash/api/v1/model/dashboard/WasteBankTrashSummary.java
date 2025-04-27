package com.gotrash.api.v1.model.dashboard;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class WasteBankTrashSummary {
  private UUID wasteBankId;
  private String wasteBankName;
  private BigDecimal totalWeight;
}
