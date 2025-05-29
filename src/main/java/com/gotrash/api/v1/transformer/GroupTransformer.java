package com.gotrash.api.v1.transformer;

import com.gotrash.api.v1.model.*;
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
        .name(groupEntity.getName())
        .owner(CitizenTransformer.transformEntityToModel(groupEntity.getOwner()))
        .groupMembers(groupMembers)
        .createdAt(groupEntity.getCreatedAt())
        .updatedAt(groupEntity.getUpdatedAt())
        .build();
  }

  public static GroupEntity transformModelToEntity(Group group) {
    return GroupEntity.builder()
        .groupId(group.getGroupId() != null ? UUID.fromString(group.getGroupId()) : null)
        .owner(group.getOwner() != null && group.getOwner().getUserId() != null ? CitizenTransformer.transformModelToEntity(group.getOwner()) : null)
        .name(group.getName())
        .build();
  }

  public static Group transformRequestToModel(String groupId, GroupRequest groupRequest) {
    return Group.builder()
        .groupId(groupId)
        .owner(Citizen.builder().userId(groupRequest.getCitizenId()).build())
        .name(groupRequest.getName())
        .build();
  }

  public static Group transformRequestToModel(GroupRequest groupRequest) {
    return Group.builder()
        .owner(Citizen.builder().userId(groupRequest.getCitizenId()).build())
        .name(groupRequest.getName())
        .build();
  }

  public static GroupResponse transformModelToResponse(Group group) {

    return GroupResponse.builder()
        .groupId(group.getGroupId())
        .name(group.getName())
        .owner(CitizenTransformer.transformModelToResponse(group.getOwner()))
        .groupMembers(
            group.getGroupMembers().stream()
                .map(GroupMemberTransformer::transformModelToResponse)
                .toList()
        )
        .groupMissionProgress(null)
        .createdAt(group.getCreatedAt())
        .updatedAt(group.getUpdatedAt())
        .build();
  }


  public static GroupResponse transformModelToResponse(Group group,
                                                       GroupMissionProgress groupMissionProgress,
                                                       List<GroupMemberMissionContribution> groupMemberMissionContributions) {

    return GroupResponse.builder()
        .groupId(group.getGroupId())
        .name(group.getName())
        .owner(CitizenTransformer.transformModelToResponse(group.getOwner()))
        .groupMembers(
            group.getGroupMembers().stream()
                .map(GroupMemberTransformer::transformModelToResponse)
                .toList()
        )
        .groupMissionProgress(GroupMissionProgressTransformer.transformModelToResponse(
            groupMissionProgress, groupMemberMissionContributions
        ))
        .createdAt(group.getCreatedAt())
        .updatedAt(group.getUpdatedAt())
        .build();
  }
  
}
