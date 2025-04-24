package com.gotrash.api.v1.response;

import com.gotrash.api.v1.model.Company;
import com.gotrash.api.v1.model.WasteBank;
import com.gotrash.constant.ShipmentStatus;
import com.gotrash.constant.ShipmentTrashCategory;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ShipmentResponse {
  private String shipmentId;
  private WasteBankResponse wasteBank;
  private ShipmentTrashCategory category;
  private Double weight;
  private CompanyResponse destinationCompany;
  private Double price;
  private ShipmentStatus status;
  private LocalDateTime createdAt;
  private LocalDateTime updatedAt;
}
