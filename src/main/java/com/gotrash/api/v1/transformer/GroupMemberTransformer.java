package com.gotrash.api.v1.transformer;

import com.gotrash.api.v1.model.Citizen;
import com.gotrash.api.v1.model.Group;
import com.gotrash.api.v1.model.GroupMember;
import com.gotrash.api.v1.model.User;
import com.gotrash.api.v1.request.group.GroupMemberRequest;
import com.gotrash.api.v1.response.GroupMemberResponse;
import com.gotrash.entity.GroupMemberEntity;

import java.util.UUID;

public class GroupMemberTransformer {

  public static GroupMember transformEntityToModel(GroupMemberEntity groupMemberEntity) {
    return GroupMember.builder()
        .groupMemberId(groupMemberEntity.getGroupMemberId().toString())
        .citizen(CitizenTransformer.transformEntityToModel(groupMemberEntity.getCitizen()))
        .build();
  }

  public static GroupMemberEntity transformModelToEntity(GroupMember groupMember) {
    return GroupMemberEntity.builder()
        .groupMemberId(groupMember.getGroupMemberId() != null ? UUID.fromString(groupMember.getGroupMemberId()) : null)
        .citizen(CitizenTransformer.transformModelToEntity(groupMember.getCitizen()))
        .group(GroupTransformer.transformModelToEntity(groupMember.getGroup()))
        .build();
  }

  public static GroupMember transformRequestToModel(GroupMemberRequest groupMemberRequest) {
    return GroupMember.builder()
        .citizen(Citizen.builder().userId(groupMemberRequest.getCitizenId()).build())
        .build();
  }

  public static GroupMember transformRequestToModel(String userId, String groupId) {
    return GroupMember.builder()
        .citizen(Citizen.builder().userId(userId).build())
        .group(Group.builder().groupId(groupId).build())
        .build();
  }

  public static GroupMember transformRequestToModel(GroupMemberRequest groupMemberRequest, String groupId) {
    return GroupMember.builder()
        .citizen(Citizen.builder().userId(groupMemberRequest.getCitizenId()).build())
        .group(Group.builder().groupId(groupId).build())
        .build();
  }

  public static GroupMemberResponse transformModelToResponse(GroupMember groupMember) {
    return GroupMemberResponse.builder()
        .groupMemberId(groupMember.getGroupMemberId())
        .citizen(CitizenTransformer.transformModelToResponse(groupMember.getCitizen()))
        .build();
  }
}
