package com.gotrash.api.v1.transformer;

import com.gotrash.api.v1.model.Company;
import com.gotrash.api.v1.model.Shipment;
import com.gotrash.api.v1.model.TrashCategory;
import com.gotrash.api.v1.model.WasteBank;
import com.gotrash.api.v1.request.ShipmentRequest;
import com.gotrash.api.v1.response.ShipmentResponse;
import com.gotrash.entity.ShipmentEntity;
import java.util.UUID;

public class ShipmentTransformer {

  public static ShipmentEntity transformModelToEntity(Shipment shipment) {
    return ShipmentEntity.builder()
        .shipmentId(shipment.getShipmentId() != null ? UUID.fromString(shipment.getShipmentId()) : null)
        .wasteBank(shipment.getWasteBank() != null ? WasteBankTransformer.transformModelToEntity(shipment.getWasteBank()) : null)
        .destinationCompany(shipment.getDestinationCompany() != null ? CompanyTransformer.transformModelToEntity(shipment.getDestinationCompany()) : null)
        .trashCategory(shipment.getTrashCategory() != null ? TrashCategoryTransformer.transformModelToEntity(shipment.getTrashCategory()) : null)
        .weight(shipment.getWeight())
        .price(shipment.getPrice())
        .status(shipment.getStatus())
        .createdAt(shipment.getCreatedAt())
        .updatedAt(shipment.getUpdatedAt())
        .build();
  }

  public static Shipment transformEntityToModel(ShipmentEntity shipmentEntity) {
    return Shipment.builder()
        .shipmentId(shipmentEntity.getShipmentId().toString())
        .wasteBank(WasteBankTransformer.transformEntityToModel(shipmentEntity.getWasteBank()))
        .trashCategory(TrashCategoryTransformer.transformEntityToModel(shipmentEntity.getTrashCategory()))
        .weight(shipmentEntity.getWeight())
        .destinationCompany(CompanyTransformer.transformEntityToModel(shipmentEntity.getDestinationCompany()))
        .price(shipmentEntity.getPrice())
        .status(shipmentEntity.getStatus())
        .createdAt(shipmentEntity.getCreatedAt())
        .updatedAt(shipmentEntity.getUpdatedAt())
        .build();
  }

  public static Shipment transformRequestToModel(ShipmentRequest shipmentRequest) {
    return Shipment.builder()
        .wasteBank(WasteBank.builder().userId(shipmentRequest.getWasteBankId()).build())
        .destinationCompany(Company.builder().userId(shipmentRequest.getCompanyId()).build())
        .trashCategory(TrashCategory.builder().trashCategoryId(shipmentRequest.getTrashCategoryId()).build())
        .weight(shipmentRequest.getWeight())
        .price(shipmentRequest.getPrice())
        .status(shipmentRequest.getStatus())
        .build();
  }

  public static Shipment transformRequestToModel(String shipmentId, ShipmentRequest shipmentRequest) {
    return Shipment.builder()
        .shipmentId(shipmentId)
        .wasteBank(WasteBank.builder().userId(shipmentRequest.getWasteBankId()).build())
        .destinationCompany(Company.builder().userId(shipmentRequest.getCompanyId()).build())
        .trashCategory(TrashCategory.builder().trashCategoryId(shipmentRequest.getTrashCategoryId()).build())
        .weight(shipmentRequest.getWeight())
        .price(shipmentRequest.getPrice())
        .build();
  }

  public static ShipmentResponse transformModelToResponse(Shipment shipment) {
    return ShipmentResponse.builder()
        .shipmentId(shipment.getShipmentId())
        .wasteBank(WasteBankTransformer.transformModelToResponse(shipment.getWasteBank()))
        .destinationCompany(CompanyTransformer.transformModelToResponse(shipment.getDestinationCompany()))
        .trashCategory(TrashCategoryTransformer.transformModelToResponse(shipment.getTrashCategory()))
        .weight(shipment.getWeight())
        .price(shipment.getPrice())
        .status(shipment.getStatus())
        .createdAt(shipment.getCreatedAt())
        .updatedAt(shipment.getUpdatedAt())
        .build();
  }
  
}
