package com.gotrash.api.v1;

import com.gotrash.api.response.ApiResponse;
import com.gotrash.api.response.MessageResponse;
import com.gotrash.api.v1.model.Group;
import com.gotrash.api.v1.model.GroupMember;
import com.gotrash.api.v1.model.GroupMemberMissionContribution;
import com.gotrash.api.v1.model.GroupMissionProgress;
import com.gotrash.api.v1.request.group.GroupMemberRequest;
import com.gotrash.api.v1.request.group.GroupRequest;
import com.gotrash.api.v1.response.GroupResponse;
import com.gotrash.api.v1.transformer.GroupMemberTransformer;
import com.gotrash.api.v1.transformer.GroupTransformer;
import com.gotrash.service.GroupMemberMissionContributionService;
import com.gotrash.service.GroupMissionProgressService;
import com.gotrash.service.GroupService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
@Tag(name = "Group API", description = "API for group")
public class GroupAPI {

  private final GroupService groupService;
  private final GroupMissionProgressService groupMissionProgressService;
  private final GroupMemberMissionContributionService groupMemberMissionContributionService;

  @GetMapping("/groups/{group_id}")
  @Operation(summary = "API to get group by group_id")
  public ApiResponse<GroupResponse> getGroupByUserId(@PathVariable("group_id") String groupId) {
    Group group = groupService.getGroupByGroupId(groupId);
    GroupMissionProgress groupMissionProgress = groupMissionProgressService.getActiveGroupMissionProgressByGroupId(group.getGroupId());

    GroupResponse groupResponse;

    if (groupMissionProgress == null) {
      groupResponse = GroupTransformer.transformModelToResponse(group);
    } else {
      List<String> memberIds = group.getGroupMembers()
          .stream()
          .map(groupMember -> groupMember.getCitizen().getUserId())
          .toList();

      List<GroupMemberMissionContribution> groupMemberMissionContributions = groupMemberMissionContributionService.getAllMemberActivelyContribute(
          memberIds, groupMissionProgress.getGroupMissionProgressId()
      );

      groupResponse = GroupTransformer.transformModelToResponse(
          group,
          groupMissionProgress,
          groupMemberMissionContributions
      );
    }


    return new ApiResponse<>(HttpStatus.OK.value(), groupResponse);
  }

  @GetMapping("/groups")
  @Operation(summary = "API to get all group data with optional citizen filter")
  public ApiResponse<List<GroupResponse>> getGroups(
      @RequestParam(name = "citizenId", required = false) String citizenId
  ) {
    List<Group> groups = (citizenId != null)
        ? groupService.getGroupsFilterByUserId(citizenId)
        : groupService.getGroups();

    List<GroupResponse> groupResponses = groups.stream()
        .map(group -> {
          // Get active mission progress for each group
          GroupMissionProgress groupMissionProgress = groupMissionProgressService.getActiveGroupMissionProgressByGroupId(group.getGroupId());

          GroupResponse groupResponse;

          if (groupMissionProgress == null) {
            groupResponse = GroupTransformer.transformModelToResponse(group);
          } else {
            List<String> memberIds = group.getGroupMembers()
                .stream()
                .map(groupMember -> groupMember.getCitizen().getUserId())
                .toList();

            List<GroupMemberMissionContribution> groupMemberMissionContributions = groupMemberMissionContributionService.getAllMemberActivelyContribute(
                memberIds, groupMissionProgress.getGroupMissionProgressId()
            );

            groupResponse = GroupTransformer.transformModelToResponse(
                group,
                groupMissionProgress,
                groupMemberMissionContributions
            );
          }

          return groupResponse;
        })
        .toList();

    return new ApiResponse<>(HttpStatus.OK.value(), groupResponses);
  }

  @PostMapping("/groups")
  @Operation(summary = "API to create a new group")
  public ApiResponse<GroupResponse> save(@RequestBody GroupRequest groupRequest) {
    Group group = GroupTransformer.transformRequestToModel(groupRequest);
    group = groupService.save(group);
    GroupMissionProgress groupMissionProgress = groupMissionProgressService.getActiveGroupMissionProgressByGroupId(group.getGroupId());

    GroupResponse groupResponse;

    if (groupMissionProgress == null) {
      groupResponse = GroupTransformer.transformModelToResponse(group);
    } else {
      List<String> memberIds = group.getGroupMembers()
          .stream()
          .map(groupMember -> groupMember.getCitizen().getUserId())
          .toList();

      List<GroupMemberMissionContribution> groupMemberMissionContributions = groupMemberMissionContributionService.getAllMemberActivelyContribute(
          memberIds, groupMissionProgress.getGroupMissionProgressId()
      );

      groupResponse = GroupTransformer.transformModelToResponse(
          group,
          groupMissionProgress,
          groupMemberMissionContributions
      );
    }

    return new ApiResponse<>(HttpStatus.CREATED.value(), groupResponse);
  }

  @PostMapping("/groups/{group_id}/members")
  @Operation(summary = "API to add member to group by group_id")
  public ApiResponse<MessageResponse> addMember(
      @PathVariable("group_id") String groupId,
      @RequestBody GroupMemberRequest groupMemberRequest
  ) {
    GroupMember groupMember = GroupMemberTransformer.transformRequestToModel(groupMemberRequest, groupId);
    groupService.addMember(groupMember);
    String message = "Succcessfully Add Member With Group ID " + groupMember.getGroup().getGroupId() + " And Member Id " + groupMember.getCitizen().getUserId();
    MessageResponse messageResponse = new MessageResponse(message);
    return new ApiResponse<>(HttpStatus.CREATED.value(), messageResponse);
  }

  @PatchMapping("/groups/{group_id}")
  @Operation(summary = "API to update group by group_id")
  public ApiResponse<GroupResponse> updateGroup(@PathVariable("group_id") String groupId,
                                                   @RequestBody GroupRequest groupRequest) {
    Group group = GroupTransformer.transformRequestToModel(groupId, groupRequest);
    group = groupService.update(group);

    GroupMissionProgress groupMissionProgress = groupMissionProgressService.getActiveGroupMissionProgressByGroupId(group.getGroupId());

    GroupResponse groupResponse;

    if (groupMissionProgress == null) {
      groupResponse = GroupTransformer.transformModelToResponse(group);
    } else {
      List<String> memberIds = group.getGroupMembers()
          .stream()
          .map(groupMember -> groupMember.getCitizen().getUserId())
          .toList();

      List<GroupMemberMissionContribution> groupMemberMissionContributions = groupMemberMissionContributionService.getAllMemberActivelyContribute(
          memberIds, groupMissionProgress.getGroupMissionProgressId()
      );

      groupResponse = GroupTransformer.transformModelToResponse(
          group,
          groupMissionProgress,
          groupMemberMissionContributions
      );
    }


    return new ApiResponse<>(HttpStatus.OK.value(), groupResponse);
  }

  @DeleteMapping("/groups/{group_id}/members/{member_id}")
  @Operation(summary = "API to remove member by group id and member id")
  public ApiResponse<MessageResponse> removeMember(
      @PathVariable("group_id") String groupId,
      @PathVariable("member_id") String memberId
  ) {
    GroupMember groupMember = GroupMemberTransformer.transformRequestToModel(memberId, groupId);
    groupService.removeMember(groupMember);
    String message = "Successfully Delete Member With Group ID " + groupId + " And Member Id " + memberId;
    MessageResponse messageResponse = new MessageResponse(message);
    return new ApiResponse<>(HttpStatus.OK.value(), messageResponse);
  }

  @DeleteMapping("/groups/{group_id}")
  @Operation(summary = "API to remove group by group id")
  public ApiResponse<MessageResponse> deleteGroup(@PathVariable("group_id") String groupId) {
    groupService.delete(groupId);
    String message = "Successfully Delete Group With Group ID " + groupId;
    MessageResponse messageResponse = new MessageResponse(message);
    return new ApiResponse<>(HttpStatus.OK.value(), messageResponse);
  }
}
