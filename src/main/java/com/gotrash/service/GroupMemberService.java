package com.gotrash.service;

import com.gotrash.api.v1.model.GroupMember;
import com.gotrash.api.v1.transformer.GroupMemberTransformer;
import com.gotrash.entity.GroupMemberEntity;
import com.gotrash.repository.GroupMemberRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class GroupMemberService {

  private final GroupMemberRepository groupMemberRepository;

  public GroupMember save(GroupMember groupMember) {
    GroupMemberEntity groupMemberEntity = GroupMemberTransformer.transformModelToEntity(groupMember);
    return GroupMemberTransformer.transformEntityToModel(
        groupMemberRepository.save(groupMemberEntity)
    );
  }

  public GroupMember update(GroupMember groupMember) {
    Optional<GroupMemberEntity> groupMemberEntityOptional = groupMemberRepository.findByUser_UserIdAndGroup_GroupId(
        UUID.fromString(groupMember.getCitizen().getUserId()),
        UUID.fromString(groupMember.getGroup().getGroupId())
    );

    if (groupMemberEntityOptional.isEmpty()) {
      throw new EntityNotFoundException("Group Member with user_id " + groupMember.getCitizen().getUserId() + " and group_id " + groupMember.getGroup().getGroupId() + " Not Found");
    }

    return GroupMemberTransformer.transformEntityToModel(
        groupMemberRepository.save(groupMemberEntityOptional.get())
    );
  }

  public void delete(String groupMemberId) {
    if (!groupMemberRepository.existsById(UUID.fromString(groupMemberId))) {
      throw new EntityNotFoundException("Group Member With ID " + groupMemberId + " Not Found");
    }

    groupMemberRepository.deleteById(UUID.fromString(groupMemberId));
  }
}
