package com.gotrash.api.v1.transformer;

import com.gotrash.api.v1.model.GroupMissionProgress;
import com.gotrash.api.v1.response.GroupMissionProgressResponse;
import com.gotrash.api.v1.transformer.GroupTransformer;
import com.gotrash.api.v1.transformer.MissionTransformer;
import com.gotrash.entity.GroupMissionProgressEntity;

import java.util.UUID;

public class GroupMissionProgressTransformer {

  public static GroupMissionProgress transformEntityToModel(GroupMissionProgressEntity entity) {
    return GroupMissionProgress.builder()
        .groupMissionProgressId(entity.getGroupMissionProgressId().toString())
        .mission(MissionTransformer.transformEntityToModel(entity.getMission()))
        .group(GroupTransformer.transformEntityToModel(entity.getGroup()))
        .currentProgress(entity.getCurrentProgress())
        .isRewardClaimed(entity.getIsRewardClaimed())
        .createdAt(entity.getCreatedAt())
        .updatedAt(entity.getUpdatedAt())
        .build();
  }

  public static GroupMissionProgressEntity transformModelToEntity(GroupMissionProgress model) {
    return GroupMissionProgressEntity.builder()
        .groupMissionProgressId(model.getGroupMissionProgressId() != null ?
            UUID.fromString(model.getGroupMissionProgressId()) : null)
        .mission(MissionTransformer.transformModelToEntity(model.getMission()))
        .group(GroupTransformer.transformModelToEntity(model.getGroup()))
        .currentProgress(model.getCurrentProgress())
        .isRewardClaimed(model.getIsRewardClaimed())
        .build();
  }

  public static GroupMissionProgressResponse transformModelToResponse(GroupMissionProgress model) {
    return GroupMissionProgressResponse.builder()
        .groupMissionProgressId(model.getGroupMissionProgressId())
        .mission(MissionTransformer.transformModelToResponse(model.getMission()))
        .group(GroupTransformer.transformModelToResponse(model.getGroup()))
        .currentProgress(model.getCurrentProgress())
        .isRewardClaimed(model.getIsRewardClaimed())
        .createdAt(model.getCreatedAt())
        .updatedAt(model.getUpdatedAt())
        .build();
  }
}