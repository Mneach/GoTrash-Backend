package com.gotrash.api.v1.transformer;

import com.gotrash.api.v1.model.CitizenAddress;
import com.gotrash.api.v1.model.Shipment;
import com.gotrash.api.v1.model.Reward;
import com.gotrash.api.v1.model.Citizen;
import com.gotrash.api.v1.request.ShipmentRequest;
import com.gotrash.api.v1.response.ShipmentResponse;
import com.gotrash.entity.ShipmentEntity;

import java.math.BigInteger;
import java.util.UUID;

public class ShipmentTransformer {

  public static Shipment transformRequestToModel(String shipmentId, ShipmentRequest shipmentRequest) {
    return Shipment.builder()
        .shipmentId(shipmentId)
        .citizen(Citizen.builder().userId(shipmentRequest.getCitizenId()).build())
        .reward(Reward.builder().rewardId(shipmentRequest.getRewardId()).build())
        .citizenAddress(CitizenAddress.builder().citizenAddressId(shipmentRequest.getCitizenAddressId()).build())
        .quantity(shipmentRequest.getQuantity())
        .build();
  }

  public static Shipment transformRequestToModel(ShipmentRequest shipmentRequest) {
    return Shipment.builder()
        .citizen(Citizen.builder().userId(shipmentRequest.getCitizenId()).build())
        .reward(Reward.builder().rewardId(shipmentRequest.getRewardId()).build())
        .citizenAddress(CitizenAddress.builder().citizenAddressId(shipmentRequest.getCitizenAddressId()).build())
        .quantity(shipmentRequest.getQuantity())
        .build();
  }

  public static Shipment transformEntityToModel(ShipmentEntity shipmentEntity) {
    return Shipment.builder()
        .shipmentId(shipmentEntity.getShipmentId().toString())
        .citizen(CitizenTransformer.transformEntityToModel(shipmentEntity.getCitizen()))
        .reward(RewardTransformer.transformEntityToModel(shipmentEntity.getReward()))
        .citizenAddress(CitizenAddressTransformer.transformEntityToModel(shipmentEntity.getCitizenAddress()))
        .status(shipmentEntity.getStatus())
        .quantity(shipmentEntity.getQuantity())
        .totalCoinUsed(
            shipmentEntity.getReward().getCoin().multiply(BigInteger.valueOf(shipmentEntity.getQuantity()))
        )
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
        .status(shipment.getStatus())
        .quantity(shipment.getQuantity())
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
        .status(shipment.getStatus())
        .quantity(shipment.getQuantity())
        .totalCoinUsed(
            shipment.getReward().getCoin().multiply(BigInteger.valueOf(shipment.getQuantity()))
        )
        .createdAt(shipment.getCreatedAt())
        .updatedAt(shipment.getUpdatedAt())
        .build();
  }
}
