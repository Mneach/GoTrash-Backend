package com.gotrash.api.v1.transformer;

import com.gotrash.api.v1.response.DailyMissionResponse;
import com.gotrash.api.v1.model.DailyMissionProgress;
import com.gotrash.entity.DailyMissionProgressEntity;

import java.util.UUID;

public class DailyMissionProgressTransformer {
    public static DailyMissionProgress transformEntityToModel(DailyMissionProgressEntity entity) {
      return DailyMissionProgress.builder()
          .dailyMissionProgressId(entity.getDailyMissionProgressId().toString())
          .mission(MissionTransformer.transformEntityToModel(entity.getMission()))
          .citizen(CitizenTransformer.transformEntityToModel(entity.getCitizen()))
          .activeDate(entity.getActiveDate())
          .currentProgress(entity.getCurrentProgress())
          .isRewardClaimed(entity.getIsRewardClaimed())
          .createdAt(entity.getCreatedAt())
          .updatedAt(entity.getUpdatedAt())
          .build();
    }

    public static DailyMissionProgressEntity transformModelToEntity(DailyMissionProgress model) {
      return DailyMissionProgressEntity.builder()
          .dailyMissionProgressId(model.getDailyMissionProgressId() != null ?
              UUID.fromString(model.getDailyMissionProgressId()) : null)
          .mission(MissionTransformer.transformModelToEntity(model.getMission()))
          .citizen(CitizenTransformer.transformModelToEntity(model.getCitizen()))
          .activeDate(model.getActiveDate())
          .currentProgress(model.getCurrentProgress())
          .isRewardClaimed(model.getIsRewardClaimed())
          .build();
    }

    public static DailyMissionResponse transformModelToResponse(DailyMissionProgress model) {
      return DailyMissionResponse.builder()
          .dailyMissionProgressId(model.getDailyMissionProgressId())
          .mission(MissionTransformer.transformModelToResponse(model.getMission()))
          .citizen(CitizenTransformer.transformModelToResponse(model.getCitizen()))
          .activeDate(model.getActiveDate())
          .currentProgress(model.getCurrentProgress())
          .isRewardClaimed(model.getIsRewardClaimed())
          .createdAt(model.getCreatedAt())
          .updatedAt(model.getUpdatedAt())
          .build();
    }
}
