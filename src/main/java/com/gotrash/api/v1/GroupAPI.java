package com.gotrash.api.v1;

import com.gotrash.api.response.MessageResponse;
import com.gotrash.api.v1.model.Exchange;
import com.gotrash.api.v1.model.Group;
import com.gotrash.api.v1.model.GroupMember;
import com.gotrash.api.v1.request.group.GroupMemberRequest;
import com.gotrash.api.v1.request.group.GroupRequest;
import com.gotrash.api.v1.response.GroupResponse;
import com.gotrash.api.v1.response.GroupResponse;
import com.gotrash.api.v1.transformer.ExchangeTransformer;
import com.gotrash.api.v1.transformer.GroupMemberTransformer;
import com.gotrash.api.v1.transformer.GroupTransformer;
import com.gotrash.service.GroupService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("v1")
@Tag(name = "Group", description = "API for group")
public class GroupAPI {

  private final GroupService groupService;

  @GetMapping("/groups/{group_id}")
  @Operation(summary = "API to get group by group_id")
  public ResponseEntity<GroupResponse> getGroupByUserId(@PathVariable("group_id") String groupId) {
    GroupResponse groupResponse = GroupTransformer.transformModelToResponse(groupService.getGroupByGroupId(groupId));
    return new ResponseEntity<>(groupResponse, HttpStatus.OK);
  }

  @GetMapping("/groups")
  @Operation(summary = "API to get all group data")
  public ResponseEntity<List<GroupResponse>> getExchanges() {
    List<Group> groups = groupService.getGroups();
    List<GroupResponse> groupResponses = groups.stream()
        .map(GroupTransformer::transformModelToResponse)
        .toList();
    return new ResponseEntity<>(groupResponses, HttpStatus.OK);
  }

  @GetMapping("/groups/users/{user_id}")
  @Operation(summary = "API to get groups by user_id")
  public ResponseEntity<List<GroupResponse>> getGroupsByUserId(@PathVariable("user_id") String userId) {
    List<Group> groups = groupService.getGroupsByUserId(userId);
    List<GroupResponse> groupResponses = groups.stream()
        .map(GroupTransformer::transformModelToResponse)
        .toList();
    return new ResponseEntity<>(groupResponses, HttpStatus.OK);
  }

  @PostMapping("/groups")
  @Operation(summary = "API to create a new group")
  public ResponseEntity<GroupResponse> groupResponse(@RequestBody GroupRequest groupRequest) {
    Group group = GroupTransformer.transformRequestToModel(groupRequest);
    GroupResponse groupResponse = GroupTransformer.transformModelToResponse(groupService.save(group));
    return new ResponseEntity<>(groupResponse, HttpStatus.CREATED);
  }

  @PostMapping("/groups/{group_id}/members")
  @Operation(summary = "API to add member to group by group_id")
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

  @PatchMapping("/groups/{group_id}")
  @Operation(summary = "API to update group by group_id")
  public ResponseEntity<GroupResponse> updateGroup(@RequestBody GroupRequest groupRequest) {
    Group group = GroupTransformer.transformRequestToModel(groupRequest);
    GroupResponse groupResponse = GroupTransformer.transformModelToResponse(groupService.update(group));

    return new ResponseEntity<>(groupResponse, HttpStatus.OK);
  }

  @DeleteMapping("/groups/{group_id}/members/{member_id}")
  @Operation(summary = "API to remove member by group_id and member_id")
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
