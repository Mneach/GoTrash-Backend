package com.gotrash.api.v1.model;

import com.gotrash.constant.ShipmentStatus;
import com.gotrash.constant.ShipmentTrashCategory;
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
public class Shipment {
  private String shipmentId;
  private WasteBank wasteBank;
  private TrashCategory trashCategory;
  private BigDecimal weight;
  private Company destinationCompany;
  private BigDecimal price;
  private String status;
  private LocalDateTime createdAt;
  private LocalDateTime updatedAt;
}
