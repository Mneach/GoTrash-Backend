package com.gotrash.api.v1.transformer;

import com.gotrash.api.v1.model.Mission;
import com.gotrash.api.v1.model.Trash;
import com.gotrash.api.v1.request.MissionRequest;
import com.gotrash.api.v1.response.MissionResponse;
import com.gotrash.api.v1.request.MissionUpdateRequest;
import com.gotrash.entity.MissionEntity;

import java.util.UUID;

public class MissionTransformer {

  public static Mission transformEntityToModel(MissionEntity missionEntity) {
    return Mission.builder()
        .missionId(missionEntity.getMissionId().toString())
        .type(missionEntity.getType())
        .goalType(missionEntity.getGoalType())
        .title(missionEntity.getTitle())
        .description(missionEntity.getDescription())
        .targetValue(missionEntity.getTargetValue())
        .trash(
            missionEntity.getTrash() != null ? TrashTransformer.transformEntityToModel(missionEntity.getTrash()) : null
        )
        .rewardCoins(missionEntity.getRewardCoins())
        .rewardRatings(missionEntity.getRewardRatings())
        .createdAt(missionEntity.getCreatedAt())
        .updatedAt(missionEntity.getUpdatedAt())
        .build();
  }

  public static MissionEntity transformModelToEntity(Mission mission) {
    return MissionEntity.builder()
        .missionId(mission.getMissionId() != null ? UUID.fromString(mission.getMissionId()) : null)
        .type(mission.getType())
        .goalType(mission.getGoalType())
        .title(mission.getTitle())
        .description(mission.getDescription())
        .targetValue(mission.getTargetValue())
        .trash(
            mission.getTrash() != null && mission.getTrash().getTrashId() != null  ?
            TrashTransformer.transformModelToEntity(mission.getTrash()) : null)
        .rewardCoins(mission.getRewardCoins())
        .rewardRatings(mission.getRewardRatings())
        .build();
  }

  public static Mission transformRequestToModel(String missionId, MissionUpdateRequest missionUpdateRequest) {
    return Mission.builder()
        .missionId(missionId)
        .title(missionUpdateRequest.getTitle())
        .description(missionUpdateRequest.getDescription())
        .rewardCoins(missionUpdateRequest.getRewardCoins())
        .rewardRatings(missionUpdateRequest.getRewardRatings())
        .build();
  }

  public static Mission transformRequestToModel(MissionRequest missionRequest) {
    return Mission.builder()
        .type(missionRequest.getType())
        .goalType(missionRequest.getGoalType())
        .title(missionRequest.getTitle())
        .description(missionRequest.getDescription())
        .targetValue(missionRequest.getTargetValue())
        .trash(Trash.builder().trashId(missionRequest.getTrashId()).build())
        .rewardCoins(missionRequest.getRewardCoins())
        .rewardRatings(missionRequest.getRewardRatings())
        .build();
  }

  public static MissionResponse transformModelToResponse(Mission mission) {
    return MissionResponse.builder()
        .missionId(mission.getMissionId())
        .type(mission.getType())
        .goalType(mission.getGoalType())
        .title(mission.getTitle())
        .description(mission.getDescription())
        .targetValue(mission.getTargetValue())
        .trash(
            mission.getTrash() != null ? TrashTransformer.transformModelToResponse(mission.getTrash()) : null
        )
        .rewardCoins(mission.getRewardCoins())
        .rewardRatings(mission.getRewardRatings())
        .createdAt(mission.getCreatedAt())
        .updatedAt(mission.getUpdatedAt())
        .build();
  }
}