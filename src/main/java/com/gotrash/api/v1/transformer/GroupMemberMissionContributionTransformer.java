package com.gotrash.api.v1.transformer;

import com.gotrash.api.v1.model.GroupMemberMissionContribution;
import com.gotrash.api.v1.response.GroupMemberMissionContributionResponse;
import com.gotrash.entity.GroupMemberMissionContributionEntity;

import java.util.UUID;

public class GroupMemberMissionContributionTransformer {

  public static GroupMemberMissionContribution transformEntityToModel(GroupMemberMissionContributionEntity entity) {
    return GroupMemberMissionContribution.builder()
        .contributionId(entity.getContributionId().toString())
        .groupMissionProgress(GroupMissionProgressTransformer.transformEntityToModel(entity.getGroupMissionProgress()))
        .citizen(CitizenTransformer.transformEntityToModel(entity.getCitizen()))
        .contribution(entity.getContribution())
        .createdAt(entity.getCreatedAt())
        .updatedAt(entity.getUpdatedAt())
        .build();
  }

  public static GroupMemberMissionContributionEntity transformModelToEntity(GroupMemberMissionContribution model) {
    return GroupMemberMissionContributionEntity.builder()
        .contributionId(model.getContributionId() != null ?
            UUID.fromString(model.getContributionId()) : null)
        .groupMissionProgress(GroupMissionProgressTransformer.transformModelToEntity(model.getGroupMissionProgress()))
        .citizen(CitizenTransformer.transformModelToEntity(model.getCitizen()))
        .contribution(model.getContribution())
        .build();
  }

  public static GroupMemberMissionContributionResponse transformModelToResponse(GroupMemberMissionContribution model) {
    return GroupMemberMissionContributionResponse.builder()
        .contributionId(model.getContributionId())
        .citizen(CitizenTransformer.transformModelToResponse(model.getCitizen()))
        .contribution(model.getContribution())
        .createdAt(model.getCreatedAt())
        .updatedAt(model.getUpdatedAt())
        .build();
  }
}
