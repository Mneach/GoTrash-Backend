package com.gotrash.api.v1.transformer;

import com.gotrash.api.v1.model.Group;
import com.gotrash.api.v1.model.GroupMemberMissionContribution;
import com.gotrash.api.v1.model.GroupMissionProgress;
import com.gotrash.api.v1.model.Mission;
import com.gotrash.api.v1.request.GroupMissionProgressRequest;
import com.gotrash.api.v1.response.GroupMissionProgressResponse;
import com.gotrash.api.v1.transformer.GroupTransformer;
import com.gotrash.api.v1.transformer.MissionTransformer;
import com.gotrash.entity.GroupMissionProgressEntity;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public class GroupMissionProgressTransformer {

  public static GroupMissionProgress transformRequestToModel(GroupMissionProgressRequest groupMissionProgressRequest) {
    return GroupMissionProgress.builder()
        .group(Group.builder().groupId(groupMissionProgressRequest.getGroupId()).build())
        .mission(Mission.builder().missionId(groupMissionProgressRequest.getMissionId()).build())
        .currentProgress(BigDecimal.ZERO)
        .isRewardClaimed(false)
        .build();
  }

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
        .mission(model.getMission() != null && model.getMission().getMissionId() != null ? MissionTransformer.transformModelToEntity(model.getMission()) : null )
        .group(model.getGroup() != null && model.getGroup().getGroupId() != null ? GroupTransformer.transformModelToEntity(model.getGroup()) : null)
        .currentProgress(model.getCurrentProgress())
        .isRewardClaimed(model.getIsRewardClaimed())
        .build();
  }

  public static GroupMissionProgressResponse transformModelToResponse(GroupMissionProgress model,
                                                                      List<GroupMemberMissionContribution> groupMemberMissionContributionList) {
    return GroupMissionProgressResponse.builder()
        .groupMissionProgressId(model.getGroupMissionProgressId())
        .mission(MissionTransformer.transformModelToResponse(model.getMission()))
        .groupMemberMissionContributions(
            groupMemberMissionContributionList
                .stream()
                .map(GroupMemberMissionContributionTransformer::transformModelToResponse)
                .toList()
        )
        .currentProgress(model.getCurrentProgress())
        .isRewardClaimed(model.getIsRewardClaimed())
        .createdAt(model.getCreatedAt())
        .updatedAt(model.getUpdatedAt())
        .build();
  }
}