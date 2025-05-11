package com.gotrash.api.v1.transformer;

import com.gotrash.api.v1.model.WasteBank;
import com.gotrash.api.v1.model.WasteBankWarehouse;
import com.gotrash.api.v1.response.WasteBankResponse;
import com.gotrash.api.v1.response.WasteBankWarehouseResponse;
import com.gotrash.entity.WasteBankEntity;
import com.gotrash.entity.WasteBankWarehouseEntity;
import com.gotrash.entity.id.WasteBankWarehouseId;

import java.util.UUID;

public class WasteBankWarehouseTransformer {

  public static WasteBankWarehouse transformEntityToModel(WasteBankWarehouseEntity wasteBankWarehouseEntity) {
    return WasteBankWarehouse.builder()
        .wasteBankWarehouseId(wasteBankWarehouseEntity.getWasteBankWarehouseId())
        .wasteBank(WasteBankTransformer.transformEntityToModel(wasteBankWarehouseEntity.getWasteBankEntity()))
        .trashCategory(TrashCategoryTransformer.transformEntityToModel(wasteBankWarehouseEntity.getTrashCategoryEntity()))
        .createdAt(wasteBankWarehouseEntity.getCreatedAt())
        .updatedAt(wasteBankWarehouseEntity.getUpdatedAt())
        .build();
  }

  public static WasteBankWarehouseEntity transformModelToEntity(WasteBankWarehouse wasteBankWarehouse) {
    return WasteBankWarehouseEntity.builder()
        .wasteBankWarehouseId(wasteBankWarehouse.getWasteBankWarehouseId())
        .wasteBankEntity(wasteBankWarehouse.getWasteBank() != null ? WasteBankTransformer.transformModelToEntity(wasteBankWarehouse.getWasteBank()) : null)
        .trashCategoryEntity(wasteBankWarehouse.getTrashCategory() != null ? TrashCategoryTransformer.transformModelToEntity(wasteBankWarehouse.getTrashCategory()) : null)
        .totalWeight(wasteBankWarehouse.getTotalWeight())
        .build();
  }

  public static WasteBankWarehouseResponse transformModelToResponse(WasteBankWarehouse wasteBankWarehouse) {
    return WasteBankWarehouseResponse.builder()
        .wasteBankWarehouseId(wasteBankWarehouse.getWasteBankWarehouseId())
        .trashCategoryResponse(TrashCategoryTransformer.transformModelToResponse(wasteBankWarehouse.getTrashCategory()))
        .totalWeight(wasteBankWarehouse.getTotalWeight())
        .createdAt(wasteBankWarehouse.getCreatedAt())
        .updatedAt(wasteBankWarehouse.getUpdatedAt())
        .build();
  }
}
