package com.gotrash.api.v1;

import com.gotrash.api.response.MessageResponse;
import com.gotrash.api.v1.model.Group;
import com.gotrash.api.v1.model.GroupMember;
import com.gotrash.api.v1.request.group.GroupMemberRequest;
import com.gotrash.api.v1.request.group.GroupRequest;
import com.gotrash.api.v1.response.GroupResponse;
import com.gotrash.api.v1.transformer.GroupMemberTransformer;
import com.gotrash.api.v1.transformer.GroupTransformer;
import com.gotrash.service.GroupService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("v1")
public class GroupAPI {

  private final GroupService groupService;

  @GetMapping("/groups/{group_id}")
  public ResponseEntity<GroupResponse> groupResponse(@PathVariable("group_id") String groupId) {
    GroupResponse groupResponse = GroupTransformer.transformModelToResponse(groupService.getGroupByGroupId(groupId));
    return new ResponseEntity<>(groupResponse, HttpStatus.OK);
  }

  @PostMapping("/groups")
  public ResponseEntity<GroupResponse> groupResponse(@RequestBody GroupRequest groupRequest) {
    Group group = GroupTransformer.transformRequestToModel(groupRequest);
    GroupResponse groupResponse = GroupTransformer.transformModelToResponse(groupService.save(group));
    return new ResponseEntity<>(groupResponse, HttpStatus.CREATED);
  }

  @PostMapping("/groups/{group_id}/members")
  public ResponseEntity<MessageResponse> addMember(
      @PathVariable("group_id") String groupId,
      @RequestBody GroupMemberRequest groupMemberRequest
  ) {
    GroupMember groupMember = GroupMemberTransformer.transformRequestToModel(groupMemberRequest, groupId);
    groupService.addMember(groupMember);
    String message = "Succcessfully Add Member With Group ID " + groupMember.getGroup().getGroupId() + " And Member Id " + groupMember.getUser().getUserId();
    MessageResponse messageResponse = new MessageResponse(message);
    return new ResponseEntity<>(messageResponse, HttpStatus.CREATED);
  }

  @DeleteMapping("/groups/{group_id}/members/{member_id}")
  public ResponseEntity<MessageResponse> removeMember(
      @PathVariable("group_id") String groupId,
      @PathVariable("member_id") String memberId
  ) {
    GroupMember groupMember = GroupMemberTransformer.transformRequestToModel(memberId, groupId);
    groupService.removeMember(groupMember);
    String message = "Successfully Delete Member With Group ID " + groupId + " And Member Id " + memberId;
    MessageResponse messageResponse = new MessageResponse(message);
    return new ResponseEntity<>(messageResponse, HttpStatus.OK);
  }
}
