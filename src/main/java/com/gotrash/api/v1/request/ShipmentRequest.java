package com.gotrash.api.v1.request;

import com.gotrash.constant.ShipmentStatus;
import com.gotrash.constant.ShipmentTrashCategory;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ShipmentRequest {
  private String wasteBankId;
  private String companyId;
  private ShipmentTrashCategory category;
  private Double weight;
  private Double price;
  private ShipmentStatus status;
}
