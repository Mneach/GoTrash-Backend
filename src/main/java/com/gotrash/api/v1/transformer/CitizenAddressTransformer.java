package com.gotrash.api.v1.transformer;

import com.gotrash.api.v1.model.Citizen;
import com.gotrash.api.v1.model.CitizenAddress;
import com.gotrash.api.v1.model.Group;
import com.gotrash.api.v1.model.trashhistory.TrashHistory;
import com.gotrash.api.v1.request.CitizenAddressRequest;
import com.gotrash.api.v1.request.auth.RegisterCitizenRequest;
import com.gotrash.api.v1.response.CitizenAddressResponse;
import com.gotrash.api.v1.transformer.trashhistory.TrashHistoryTransformer;
import com.gotrash.entity.CitizenAddressEntity;

import java.util.List;
import java.util.UUID;

public class CitizenAddressTransformer {

  public static CitizenAddressEntity transformModelToEntity(CitizenAddress citizenAddress) {
    return CitizenAddressEntity.builder()
        .citizenAddressId(UUID.fromString(citizenAddress.getCitizenAddressId()))
        .citizen(CitizenTransformer.transformModelToEntity(citizenAddress.getCitizen()))
        .label(citizenAddress.getLabel())
        .address(citizenAddress.getAddress())
        .note(citizenAddress.getNote())
        .createdAt(citizenAddress.getCreatedAt())
        .updatedAt(citizenAddress.getUpdatedAt())
        .build();
  }

  public static CitizenAddress transformEntityToModel(CitizenAddressEntity citizenEntity) {
    return CitizenAddress.builder()
        .citizenAddressId(citizenEntity.getCitizenAddressId().toString())
        .citizen(CitizenTransformer.transformEntityToModel(citizenEntity.getCitizen()))
        .label(citizenEntity.getLabel())
        .address(citizenEntity.getAddress())
        .note(citizenEntity.getNote())
        .createdAt(citizenEntity.getCreatedAt())
        .updatedAt(citizenEntity.getUpdatedAt())
        .build();
  }

  public static CitizenAddress transformRequestToModel(String citizenId, CitizenAddressRequest citizenAddressRequest) {
    return CitizenAddress.builder()
        .citizen(Citizen.builder().userId(citizenId).build())
        .label(citizenAddressRequest.getLabel())
        .address(citizenAddressRequest.getAddress())
        .note(citizenAddressRequest.getNote())
        .build();
  }

  public static CitizenAddress transformRequestToModel(String citizenId,
                                                       String citizenAddressId,
                                                       CitizenAddressRequest citizenAddressRequest) {
    return CitizenAddress.builder()
        .citizenAddressId(citizenAddressId)
        .citizen(Citizen.builder().userId(citizenId).build())
        .label(citizenAddressRequest.getLabel())
        .address(citizenAddressRequest.getAddress())
        .note(citizenAddressRequest.getNote())
        .build();
  }

  public static CitizenAddressResponse transformModelToResponse(CitizenAddress citizenAddress) {
    return CitizenAddressResponse.builder()
        .citizenAddressId(citizenAddress.getCitizenAddressId())
        .label(citizenAddress.getLabel())
        .address(citizenAddress.getAddress())
        .note(citizenAddress.getNote())
        .createdAt(citizenAddress.getCreatedAt())
        .updatedAt(citizenAddress.getUpdatedAt())
        .build();
  }
}
