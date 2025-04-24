package com.gotrash.api.v1.transformer;

import com.gotrash.api.v1.model.Group;
import com.gotrash.api.v1.model.GroupMember;
import com.gotrash.api.v1.model.Reward;
import com.gotrash.api.v1.model.User;
import com.gotrash.api.v1.request.group.GroupRequest;
import com.gotrash.api.v1.response.GroupResponse;
import com.gotrash.entity.GroupEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class GroupTransformer {

  public static Group transformEntityToModel(GroupEntity groupEntity) {
    List<GroupMember> groupMembers = new ArrayList<>();

    if (groupEntity.getGroupMembers() != null && !groupEntity.getGroupMembers().isEmpty()) {
      groupMembers = groupEntity.getGroupMembers().stream()
          .map(GroupMemberTransformer::transformEntityToModel)
          .toList();
    }

    return Group.builder()
        .groupId(groupEntity.getGroupId().toString())
        .reward(RewardTransformer.transformEntityToModel(groupEntity.getReward()))
        .name(groupEntity.getName())
        .coin(groupEntity.getCoin())
        .owner(UserTransformer.transformEntityToModel(groupEntity.getOwner()))
        .groupMembers(groupMembers)
        .createdAt(groupEntity.getCreatedAt())
        .updatedAt(groupEntity.getUpdatedAt())
        .build();
  }

  public static GroupEntity transformModelToEntity(Group group) {
    return GroupEntity.builder()
        .groupId(group.getGroupId() != null ? UUID.fromString(group.getGroupId()) : null)
        .reward(RewardTransformer.transformModelToEntity(group.getReward()))
        .owner(UserTransformer.transformModelToEntity(group.getOwner()))
        .name(group.getName())
        .coin(group.getCoin())
        .build();
  }

  public static Group transformRequestToModel(String groupId, GroupRequest groupRequest) {
    return Group.builder()
        .groupId(groupId)
        .reward(Reward.builder().rewardId(groupRequest.getRewardId()).build())
        .owner(User.builder().userId(groupRequest.getUserId()).build())
        .name(groupRequest.getName())
        .coin(groupRequest.getCoin())
        .build();
  }

  public static Group transformRequestToModel(GroupRequest groupRequest) {
    return Group.builder()
        .reward(Reward.builder().rewardId(groupRequest.getRewardId()).build())
        .owner(User.builder().userId(groupRequest.getUserId()).build())
        .name(groupRequest.getName())
        .coin(groupRequest.getCoin())
        .build();
  }

  public static GroupResponse transformModelToResponse(Group group) {

    return GroupResponse.builder()
        .groupId(group.getGroupId())
        .reward(RewardTransformer.transformModelToResponse(group.getReward()))
        .name(group.getName())
        .coin(group.getCoin())
        .owner(UserTransformer.transformModelToResponse(group.getOwner()))
        .groupMembers(
            group.getGroupMembers().stream()
                .map(GroupMemberTransformer::transformModelToResponse)
                .toList()
        )
        .createdAt(group.getCreatedAt())
        .updatedAt(group.getUpdatedAt())
        .build();
  }
  
}
