package com.gotrash.api.v1.request.trashhistory;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.math.BigInteger;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TrashHistoryIoTRequest {
  private String trashName;
  private BigInteger bleId;
  private String trashBinId;
  private BigDecimal weight;
}
