package com.gotrash.api.v1.transformer;

import com.gotrash.api.v1.model.Government;
import com.gotrash.api.v1.request.GovernmentRequest;
import com.gotrash.api.v1.request.auth.RegisterGovernmentRequest;
import com.gotrash.api.v1.response.GovernmentResponse;
import com.gotrash.entity.GovernmentEntity;

import java.util.UUID;

public class GovernmentTransformer {

  public static GovernmentEntity transformModelToEntity(Government government) {
    return GovernmentEntity.builder()
        .userId(government.getUserId() != null ? UUID.fromString(government.getUserId()) : null)
        .user(government.getUser() != null ? UserTransformer.transformModelToEntity(government.getUser()) : null)
        .name(government.getName())
        .region(government.getRegion())
        .createdAt(government.getCreatedAt())
        .updatedAt(government.getUpdatedAt())
        .build();
  }

  public static Government transformEntityToModel(GovernmentEntity governmentEntity) {
    return Government.builder()
        .userId(governmentEntity.getUserId().toString())
        .user(UserTransformer.transformEntityToModel(governmentEntity.getUser()))
        .name(governmentEntity.getName())
        .email(governmentEntity.getUser().getEmail())
        .role(governmentEntity.getUser().getRole())
        .region(governmentEntity.getRegion())
        .createdAt(governmentEntity.getCreatedAt())
        .updatedAt(governmentEntity.getUpdatedAt())
        .build();
  }

  public static Government transformRequestToModel(GovernmentRequest governmentRequest) {
    return Government.builder()
        .name(governmentRequest.getName())
        .password(governmentRequest.getPassword())
        .email(governmentRequest.getEmail())
        .role(governmentRequest.getRole())
        .region(governmentRequest.getRegion())
        .build();
  }

  public static Government transformRequestToModel(String userId, GovernmentRequest governmentRequest) {
    return Government.builder()
        .userId(userId)
        .name(governmentRequest.getName())
        .password(governmentRequest.getPassword())
        .email(governmentRequest.getEmail())
        .role(governmentRequest.getRole())
        .region(governmentRequest.getRegion())
        .build();
  }

  public static Government transformRequestToModel(RegisterGovernmentRequest registerGovernmentRequest) {
    return Government.builder()
        .name(registerGovernmentRequest.getName())
        .password(registerGovernmentRequest.getPassword())
        .email(registerGovernmentRequest.getEmail())
        .role(registerGovernmentRequest.getRole())
        .region(registerGovernmentRequest.getRegion())
        .build();
  }

  public static GovernmentResponse transformModelToResponse(Government government) {
    return GovernmentResponse.builder()
        .userId(government.getUserId())
        .email(government.getEmail())
        .role(government.getRole())
        .name(government.getName())
        .region(government.getRegion())
        .createdAt(government.getCreatedAt())
        .updatedAt(government.getUpdatedAt())
        .build();
  }
}
