package com.gotrash.api.v1.transformer;

import com.gotrash.api.v1.model.Group;
import com.gotrash.api.v1.model.Reward;
import com.gotrash.api.v1.request.GroupRequest;
import com.gotrash.api.v1.response.GroupResponse;
import com.gotrash.entity.GroupEntity;

import java.util.UUID;

public class GroupTransformer {

  public static Group transformEntityToModel(GroupEntity groupEntity) {
    return Group.builder()
        .groupId(groupEntity.getGroupId().toString())
        .reward(RewardTransformer.transformEntityToModel(groupEntity.getReward()))
        .name(groupEntity.getName())
        .coin(groupEntity.getCoin())
        .createdAt(groupEntity.getCreatedAt())
        .updatedAt(groupEntity.getUpdatedAt())
        .build();
  }

  public static GroupEntity transformModelToEntity(Group group) {
    return GroupEntity.builder()
        .groupId(group.getGroupId() != null ? UUID.fromString(group.getGroupId()) : null)
        .reward(RewardTransformer.transformModelToEntity(group.getReward()))
        .name(group.getName())
        .coin(group.getCoin())
        .build();
  }

  public static Group transformRequestToModel(GroupRequest groupRequest) {
    return Group.builder()
        .groupId(groupRequest.getGroupId())
        .reward(Reward.builder().rewardId(groupRequest.getRewardId()).build())
        .name(groupRequest.getName())
        .coin(groupRequest.getCoin())
        .build();
  }

  public static GroupResponse transformModelToResponse(Group group) {
    return GroupResponse.builder()
        .groupId(group.getGroupId())
        .reward(group.getReward())
        .name(group.getName())
        .coin(group.getCoin())
        .createdAt(group.getCreatedAt())
        .updatedAt(group.getUpdatedAt())
        .build();
  }
  
}
