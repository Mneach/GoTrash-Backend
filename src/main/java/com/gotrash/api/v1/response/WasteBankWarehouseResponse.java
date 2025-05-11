package com.gotrash.api.v1.response;


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
public class WasteBankWarehouseResponse {
  private String wasteBankId;
  private TrashCategoryResponse trashCategory;
  private BigDecimal totalWeight;
  private LocalDateTime createdAt;
  private LocalDateTime updatedAt;
}
