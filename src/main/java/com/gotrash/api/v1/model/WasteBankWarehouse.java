package com.gotrash.api.v1.model;

import com.gotrash.entity.id.WasteBankWarehouseId;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class WasteBankWarehouse {
  private WasteBankWarehouseId wasteBankWarehouseId;
  private WasteBank wasteBank;
  private TrashCategory trashCategory;
  private BigDecimal totalWeight;
  private LocalDateTime createdAt;
  private LocalDateTime updatedAt;
}
