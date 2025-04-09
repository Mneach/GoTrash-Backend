package com.gotrash.service;

import com.gotrash.api.v1.model.GroupMember;
import com.gotrash.repository.GroupMemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GroupMemberService {

  private final GroupMemberRepository groupMemberRepository;
  private final UserService userService;
  private final GroupService groupService;

  public GroupMember save(GroupMember groupMember) {

  }
}
