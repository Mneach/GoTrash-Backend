package com.gotrash.api.v1.transformer;

import com.gotrash.api.v1.model.Citizen;
import com.gotrash.api.v1.model.Group;
import com.gotrash.api.v1.model.TrashHistory;
import com.gotrash.api.v1.request.CitizenRequest;
import com.gotrash.api.v1.request.auth.RegisterCitizenRequest;
import com.gotrash.api.v1.response.CitizenResponse;
import com.gotrash.entity.CitizenEntity;
import com.gotrash.api.v1.transformer.TrashHistoryTransformer;
import com.gotrash.api.v1.transformer.GroupTransformer;

import java.util.List;
import java.util.UUID;

public class CitizenTransformer {

    public static CitizenEntity transformModelToEntity(Citizen citizen) {
      return CitizenEntity.builder()
          .userId(citizen.getUserId() != null ? UUID.fromString(citizen.getUserId()) : null)
          .user(citizen.getUser() != null ? UserTransformer.transformModelToEntity(citizen.getUser()) : null)
          .name(citizen.getName())
          .phoneNumber(citizen.getPhoneNumber())
          .imageUrl(citizen.getImageUrl())
          .coin(citizen.getCoin())
          .currentStreak(citizen.getCurrentStreak())
          .longestStreak(citizen.getLongestStreak())
          .lastTrashDate(citizen.getLastTrashDate())
          .rating(citizen.getRating())
          .createdAt(citizen.getCreatedAt())
          .updatedAt(citizen.getUpdatedAt())
          .build();
    }

    public static Citizen transformEntityToModel(CitizenEntity citizenEntity) {
      return Citizen.builder()
          .userId(citizenEntity.getUserId().toString())
          .user(UserTransformer.transformEntityToModel(citizenEntity.getUser()))
          .email(citizenEntity.getUser().getEmail())
          .role(citizenEntity.getUser().getRole())
          .name(citizenEntity.getName())
          .phoneNumber(citizenEntity.getPhoneNumber())
          .imageUrl(citizenEntity.getImageUrl())
          .coin(citizenEntity.getCoin())
          .rating(citizenEntity.getRating())
          .currentStreak(citizenEntity.getCurrentStreak())
          .longestStreak(citizenEntity.getLongestStreak())
          .lastTrashDate(citizenEntity.getLastTrashDate())
          .createdAt(citizenEntity.getCreatedAt())
          .updatedAt(citizenEntity.getUpdatedAt())
          .build();
    }

  public static Citizen transformRequestToModel(String userId, CitizenRequest citizenRequest) {
    return Citizen.builder()
        .userId(userId)
        .name(citizenRequest.getName())
        .password(citizenRequest.getPassword())
        .email(citizenRequest.getEmail())
        .phoneNumber(citizenRequest.getPhoneNumber())
        .imageUrl(citizenRequest.getImageUrl())
        .coin(citizenRequest.getCoin())
        .rating(citizenRequest.getRating())
        .role(citizenRequest.getRole())
        .currentStreak(citizenRequest.getCurrentStreak())
        .longestStreak(citizenRequest.getLongestStreak())
        .lastTrashDate(citizenRequest.getLastTrashDate())
        .build();
  }

    public static Citizen transformRequestToModel(CitizenRequest citizenRequest) {
      return Citizen.builder()
          .name(citizenRequest.getName())
          .password(citizenRequest.getPassword())
          .email(citizenRequest.getEmail())
          .phoneNumber(citizenRequest.getPhoneNumber())
          .imageUrl(citizenRequest.getImageUrl())
          .coin(citizenRequest.getCoin())
          .rating(citizenRequest.getRating())
          .role(citizenRequest.getRole())
          .currentStreak(citizenRequest.getCurrentStreak())
          .longestStreak(citizenRequest.getLongestStreak())
          .lastTrashDate(citizenRequest.getLastTrashDate())
          .build();
    }

  public static Citizen transformRequestToModel(RegisterCitizenRequest registerCitizenRequest) {
    return Citizen.builder()
        .name(registerCitizenRequest.getName())
        .password(registerCitizenRequest.getPassword())
        .email(registerCitizenRequest.getEmail())
        .phoneNumber(registerCitizenRequest.getPhoneNumber())
        .imageUrl(registerCitizenRequest.getImageUrl())
        .coin(registerCitizenRequest.getCoin())
        .rating(registerCitizenRequest.getRating())
        .role(registerCitizenRequest.getRole())
        .build();
  }

  public static CitizenResponse transformModelToResponse(Citizen citizen) {
    return CitizenResponse.builder()
        .userId(citizen.getUserId())
        .name(citizen.getName())
        .email(citizen.getEmail())
        .role(citizen.getRole())
        .phoneNumber(citizen.getPhoneNumber())
        .imageUrl(citizen.getImageUrl())
        .coin(citizen.getCoin())
        .rating(citizen.getRating())
        .role(citizen.getRole())
        .currentStreak(citizen.getCurrentStreak())
        .longestStreak(citizen.getLongestStreak())
        .lastTrashDate(citizen.getLastTrashDate())
        .createdAt(citizen.getCreatedAt())
        .updatedAt(citizen.getUpdatedAt())
        .build();
  }

    public static CitizenResponse transformModelToResponse(Citizen citizen,
                                                           List<TrashHistory> trashHistories,
                                                           List<Group> groups) {
      return CitizenResponse.builder()
          .userId(citizen.getUserId())
          .name(citizen.getName())
          .email(citizen.getEmail())
          .role(citizen.getRole())
          .phoneNumber(citizen.getPhoneNumber())
          .imageUrl(citizen.getImageUrl())
          .coin(citizen.getCoin())
          .rating(citizen.getRating())
          .role(citizen.getRole())
          .currentStreak(citizen.getCurrentStreak())
          .longestStreak(citizen.getLongestStreak())
          .lastTrashDate(citizen.getLastTrashDate())
          .trashHistories(
              trashHistories.stream()
                  .map(TrashHistoryTransformer::transformModelToResponse)
                  .toList()
          )
          .groups(
              groups.stream()
                  .map(GroupTransformer::transformModelToResponse)
                  .toList()
          )
          .createdAt(citizen.getCreatedAt())
          .updatedAt(citizen.getUpdatedAt())
          .build();
    }
}
