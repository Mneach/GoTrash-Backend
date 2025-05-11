package com.gotrash.api.v1.transformer;

import com.gotrash.api.v1.model.WasteBank;
import com.gotrash.api.v1.model.WasteBankWarehouse;
import com.gotrash.api.v1.request.WasteBankRequest;
import com.gotrash.api.v1.request.auth.RegisterWasteBankRequest;
import com.gotrash.api.v1.response.WasteBankResponse;
import com.gotrash.entity.WasteBankEntity;

import java.util.List;
import java.util.UUID;

public class WasteBankTransformer {

  public static WasteBank transformRequestToModel(String userId, WasteBankRequest wasteBankRequest) {
    return WasteBank.builder()
        .userId(userId)
        .email(wasteBankRequest.getEmail())
        .role(wasteBankRequest.getRole())
        .name(wasteBankRequest.getName())
        .password(wasteBankRequest.getPassword())
        .latitude(wasteBankRequest.getLatitude())
        .longitude(wasteBankRequest.getLongitude())
        .address(wasteBankRequest.getAddress())
        .build();
  }

  public static WasteBank transformRequestToModel(WasteBankRequest wasteBankRequest) {
    return WasteBank.builder()
        .email(wasteBankRequest.getEmail())
        .role(wasteBankRequest.getRole())
        .name(wasteBankRequest.getName())
        .password(wasteBankRequest.getPassword())
        .latitude(wasteBankRequest.getLatitude())
        .longitude(wasteBankRequest.getLongitude())
        .address(wasteBankRequest.getAddress())
        .build();
  }

  public static WasteBank transformRequestToModel(RegisterWasteBankRequest registerWasteBankRequest) {
    return WasteBank.builder()
        .email(registerWasteBankRequest.getEmail())
        .role(registerWasteBankRequest.getRole())
        .name(registerWasteBankRequest.getName())
        .password(registerWasteBankRequest.getPassword())
        .latitude(registerWasteBankRequest.getLatitude())
        .longitude(registerWasteBankRequest.getLongitude())
        .address(registerWasteBankRequest.getAddress())
        .build();
  }

  public static WasteBank transformEntityToModel(WasteBankEntity wasteBankEntity) {
    return WasteBank.builder()
        .userId(wasteBankEntity.getUserId().toString())
        .user(UserTransformer.transformEntityToModel(wasteBankEntity.getUser()))
        .email(wasteBankEntity.getUser().getEmail())
        .role(wasteBankEntity.getUser().getRole())
        .name(wasteBankEntity.getName())
        .latitude(wasteBankEntity.getLatitude())
        .longitude(wasteBankEntity.getLongitude())
        .address(wasteBankEntity.getAddress())
        .imageUrl(wasteBankEntity.getImageUrl())
        .createdAt(wasteBankEntity.getCreatedAt())
        .updatedAt(wasteBankEntity.getUpdatedAt())
        .build();
  }

  public static WasteBankEntity transformModelToEntity(WasteBank wasteBank) {
    return WasteBankEntity.builder()
        .userId(wasteBank.getUserId() != null ? UUID.fromString(wasteBank.getUserId()) : null)
        .user(wasteBank.getUser() != null ? UserTransformer.transformModelToEntity(wasteBank.getUser()) : null)
        .name(wasteBank.getName())
        .latitude(wasteBank.getLatitude())
        .longitude(wasteBank.getLongitude())
        .address(wasteBank.getAddress())
        .imageUrl(wasteBank.getImageUrl())
        .build();
  }

  public static WasteBankResponse transformModelToResponse(WasteBank wasteBank) {
    return WasteBankResponse.builder()
        .userId(wasteBank.getUserId())
        .email(wasteBank.getEmail())
        .role(wasteBank.getRole())
        .name(wasteBank.getName())
        .latitude(wasteBank.getLatitude())
        .longitude(wasteBank.getLongitude())
        .address(wasteBank.getAddress())
        .imageUrl(wasteBank.getImageUrl())
        .createdAt(wasteBank.getCreatedAt())
        .updatedAt(wasteBank.getUpdatedAt())
        .build();
  }

  public static WasteBankResponse transformModelToResponse(WasteBank wasteBank, List<WasteBankWarehouse> wasteBankWarehouses) {
    return WasteBankResponse.builder()
        .userId(wasteBank.getUserId())
        .email(wasteBank.getEmail())
        .role(wasteBank.getRole())
        .name(wasteBank.getName())
        .latitude(wasteBank.getLatitude())
        .longitude(wasteBank.getLongitude())
        .address(wasteBank.getAddress())
        .imageUrl(wasteBank.getImageUrl())
        .wasteBankWarehouses(
            wasteBankWarehouses.stream()
                .map(WasteBankWarehouseTransformer::transformModelToResponse)
                .toList()
        )
        .createdAt(wasteBank.getCreatedAt())
        .updatedAt(wasteBank.getUpdatedAt())
        .build();
  }
}
