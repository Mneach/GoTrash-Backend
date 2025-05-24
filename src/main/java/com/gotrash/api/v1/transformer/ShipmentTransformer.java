package com.gotrash.api.v1.transformer;

import com.gotrash.api.v1.model.CitizenAddress;
import com.gotrash.api.v1.model.Shipment;
import com.gotrash.api.v1.model.Reward;
import com.gotrash.api.v1.model.Citizen;
import com.gotrash.api.v1.request.ShipmentRequest;
import com.gotrash.api.v1.response.ShipmentResponse;
import com.gotrash.entity.ShipmentEntity;

import java.util.UUID;

public class ShipmentTransformer {

  public static Shipment transformRequestToModel(String shipmentId, ShipmentRequest shipmentRequest) {
    return Shipment.builder()
        .shipmentId(shipmentId)
        .citizen(Citizen.builder().userId(shipmentRequest.getCitizenId()).build())
        .reward(Reward.builder().rewardId(shipmentRequest.getRewardId()).build())
        .citizenAddress(CitizenAddress.builder().citizenAddressId(shipmentRequest.getCitizenAddressId()).build())
        .status(shipmentRequest.getStatus())
        .description(shipmentRequest.getDescription())
        .build();
  }

  public static Shipment transformRequestToModel(ShipmentRequest shipmentRequest) {
    return Shipment.builder()
        .citizen(Citizen.builder().userId(shipmentRequest.getCitizenId()).build())
        .reward(Reward.builder().rewardId(shipmentRequest.getRewardId()).build())
        .citizenAddress(CitizenAddress.builder().citizenAddressId(shipmentRequest.getCitizenAddressId()).build())
        .status(shipmentRequest.getStatus())
        .description(shipmentRequest.getDescription())
        .build();
  }

  public static Shipment transformEntityToModel(ShipmentEntity shipmentEntity) {
    return Shipment.builder()
        .shipmentId(shipmentEntity.getShipmentId().toString())
        .citizen(CitizenTransformer.transformEntityToModel(shipmentEntity.getCitizen()))
        .reward(RewardTransformer.transformEntityToModel(shipmentEntity.getReward()))
        .citizenAddress(CitizenAddressTransformer.transformEntityToModel(shipmentEntity.getCitizenAddress()))
        .description(shipmentEntity.getDescription())
        .status(shipmentEntity.getStatus())
        .createdAt(shipmentEntity.getCreatedAt())
        .updatedAt(shipmentEntity.getUpdatedAt())
        .build();
  }

  public static ShipmentEntity transformModelToEntity(Shipment shipment) {
    return ShipmentEntity.builder()
        .shipmentId(shipment.getShipmentId() != null ? UUID.fromString(shipment.getShipmentId()) : null)
        .citizen(CitizenTransformer.transformModelToEntity(shipment.getCitizen()))
        .reward(RewardTransformer.transformModelToEntity(shipment.getReward()))
        .citizenAddress(CitizenAddressTransformer.transformModelToEntity(shipment.getCitizenAddress()))
        .description(shipment.getDescription())
        .status(shipment.getStatus())
        .createdAt(shipment.getCreatedAt())
        .updatedAt(shipment.getUpdatedAt())
        .build();
  }

  public static ShipmentResponse transformModelToResponse(Shipment shipment) {
    return ShipmentResponse.builder()
        .shipmentId(shipment.getShipmentId())
        .citizen(CitizenTransformer.transformModelToResponse(shipment.getCitizen()))
        .reward(RewardTransformer.transformModelToResponse(shipment.getReward()))
        .citizenAddress(CitizenAddressTransformer.transformModelToResponse(shipment.getCitizenAddress()))
        .description(shipment.getDescription())
        .status(shipment.getStatus())
        .createdAt(shipment.getCreatedAt())
        .updatedAt(shipment.getUpdatedAt())
        .build();
  }
}
