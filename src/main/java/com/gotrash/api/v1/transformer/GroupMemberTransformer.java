package com.gotrash.api.v1.transformer;

import com.gotrash.api.v1.model.GroupMember;
import com.gotrash.api.v1.request.GroupMemberRequest;
import com.gotrash.api.v1.response.GroupResponse;
import com.gotrash.entity.GroupMemberEntity;

public class GroupMemberTransformer {

  public static GroupMember transformEntityToModel(GroupMemberEntity groupMemberEntity) {
    return GroupMember.builder().build();
  }

  public static GroupMemberEntity transformModelToEntity(GroupMember groupMember) {
    return GroupMemberEntity.builder().build();
  }

  public static GroupMember transformRequestToModel(GroupMemberRequest groupMemberRequest) {
    return GroupMember.builder().build();
  }

  public static GroupResponse transformModelToResponse(GroupMember groupMember) {
    return GroupResponse.builder().build();
  }
}
