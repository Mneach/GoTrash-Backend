package com.gotrash.api.v1.transformer;

import com.gotrash.api.v1.model.GroupMissionContribution;
import com.gotrash.api.v1.response.GroupMissionContributionResponse;
import com.gotrash.entity.GroupMemberMissionContributionEntity;

import java.util.UUID;

public class GroupMissionContributionTransformer {

  public static GroupMissionContribution transformEntityToModel(GroupMemberMissionContributionEntity entity) {
    return GroupMissionContribution.builder()
        .contributionId(entity.getContributionId().toString())
        .groupMissionProgress(GroupMissionProgressTransformer.transformEntityToModel(entity.getGroupMissionProgress()))
        .citizen(CitizenTransformer.transformEntityToModel(entity.getCitizen()))
        .contribution(entity.getContribution())
        .createdAt(entity.getCreatedAt())
        .updatedAt(entity.getUpdatedAt())
        .build();
  }

  public static GroupMemberMissionContributionEntity transformModelToEntity(GroupMissionContribution model) {
    return GroupMemberMissionContributionEntity.builder()
        .contributionId(model.getContributionId() != null ?
            UUID.fromString(model.getContributionId()) : null)
        .groupMissionProgress(GroupMissionProgressTransformer.transformModelToEntity(model.getGroupMissionProgress()))
        .citizen(CitizenTransformer.transformModelToEntity(model.getCitizen()))
        .contribution(model.getContribution())
        .build();
  }

  public static GroupMissionContributionResponse transformModelToResponse(GroupMissionContribution model) {
    return GroupMissionContributionResponse.builder()
        .contributionId(model.getContributionId())
        .groupMissionProgress(GroupMissionProgressTransformer.transformModelToResponse(model.getGroupMissionProgress()))
        .citizen(CitizenTransformer.transformModelToResponse(model.getCitizen()))
        .contribution(model.getContribution())
        .createdAt(model.getCreatedAt())
        .updatedAt(model.getUpdatedAt())
        .build();
  }
}
