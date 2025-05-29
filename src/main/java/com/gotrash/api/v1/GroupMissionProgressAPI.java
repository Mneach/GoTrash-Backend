package com.gotrash.api.v1;

import com.gotrash.api.response.ApiResponse;
import com.gotrash.api.response.MessageResponse;
import com.gotrash.api.v1.model.GroupMemberMissionContribution;
import com.gotrash.api.v1.model.GroupMissionProgress;
import com.gotrash.api.v1.request.GroupMissionProgressRequest;
import com.gotrash.api.v1.response.GroupMissionProgressResponse;
import com.gotrash.api.v1.transformer.GroupMissionProgressTransformer;
import com.gotrash.service.GroupMemberMissionContributionService;
import com.gotrash.service.GroupMissionProgressService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
@Tag(name = "Group Mission Progress API", description = "API for Group Mission Progress")
public class GroupMissionProgressAPI {

  private final GroupMissionProgressService groupMissionProgressService;
  private final GroupMemberMissionContributionService groupMemberMissionContributionService;

  @PostMapping("/group-mission-progress")
  @Operation(summary = "Create new group mission progress")
  public ApiResponse<GroupMissionProgressResponse> createGroupMissionProgress(
      @RequestBody GroupMissionProgressRequest groupMissionProgressRequest
  ) {

    GroupMissionProgress groupMissionProgress = GroupMissionProgressTransformer.transformRequestToModel(
        groupMissionProgressRequest
    );

    groupMissionProgress = groupMissionProgressService.save(groupMissionProgress);

    List<String> memberIds = groupMissionProgress.getGroup().getGroupMembers()
        .stream()
        .map(groupMember -> groupMember.getCitizen().getUserId())
        .toList();

    List<GroupMemberMissionContribution> groupMemberMissionContributions = groupMemberMissionContributionService.getAllMemberActivelyContribute(
        memberIds, groupMissionProgress.getGroupMissionProgressId()
    );

    GroupMissionProgressResponse groupMissionProgressResponse = GroupMissionProgressTransformer.transformModelToResponse(
        groupMissionProgress, groupMemberMissionContributions
    );

    return new ApiResponse<>(HttpStatus.CREATED.value(), groupMissionProgressResponse);
  }

  @GetMapping("/group-mission-progress")
  @Operation(summary = "Get all group missions progress")
  public ApiResponse<List<GroupMissionProgressResponse>> getAllGroupMissionProgress() {
    List<GroupMissionProgress> progresses = groupMissionProgressService.getAllGroupMissionProgressRepository();

    List<GroupMissionProgressResponse> responses = progresses.stream()
        .map(progress -> {
          List<String> memberIds = progress.getGroup().getGroupMembers()
              .stream()
              .map(groupMember -> groupMember.getCitizen().getUserId().toString())
              .toList();

          List<GroupMemberMissionContribution> contributions = groupMemberMissionContributionService
              .getAllMemberActivelyContribute(
                  memberIds,
                  progress.getGroupMissionProgressId()
              );

          return GroupMissionProgressTransformer.transformModelToResponse(
              progress,
              contributions
          );
        })
        .toList();

    return new ApiResponse<>(HttpStatus.OK.value(), responses);
  }


  @GetMapping("/group-mission-progress/group/{group_id}")
  @Operation(summary = "Get all group missions progress by group ID")
  public ApiResponse<List<GroupMissionProgressResponse>> getGroupMissionsByGroupId(
      @PathVariable("group_id") String groupId) {

    List<GroupMissionProgress> progresses = groupMissionProgressService
        .getAllGroupMissionProgressFilterByGroupId(groupId);

    List<GroupMissionProgressResponse> responses = progresses.stream()
        .map(progress -> {
          List<String> memberIds = progress.getGroup().getGroupMembers()
              .stream()
              .map(groupMember -> groupMember.getCitizen().getUserId().toString())
              .toList();

          List<GroupMemberMissionContribution> contributions = groupMemberMissionContributionService
              .getAllMemberActivelyContribute(
                  memberIds,
                  progress.getGroupMissionProgressId()
              );

          return GroupMissionProgressTransformer.transformModelToResponse(
              progress,
              contributions
          );
        })
        .toList();

    return new ApiResponse<>(HttpStatus.OK.value(), responses);
  }

  @GetMapping("/group-mission-progress/group/{group_id}/active")
  @Operation(summary = "Get active group missions progress by group ID")
  public ApiResponse<GroupMissionProgressResponse> getActiveGroupMissionsByGroupId(
      @PathVariable("group_id") String groupId) {

    GroupMissionProgress groupMissionProgress = groupMissionProgressService.getActiveGroupMissionProgressByGroupId(groupId);

    GroupMissionProgressResponse groupMissionProgressResponse = null;

    if (groupMissionProgress != null) {
      List<String> memberIds = groupMissionProgress.getGroup().getGroupMembers()
          .stream()
          .map(groupMember -> groupMember.getCitizen().getUserId())
          .toList();

      List<GroupMemberMissionContribution> groupMemberMissionContributions = groupMemberMissionContributionService.getAllMemberActivelyContribute(
          memberIds, groupMissionProgress.getGroupMissionProgressId()
      );

      groupMissionProgressResponse = GroupMissionProgressTransformer.transformModelToResponse(
          groupMissionProgress, groupMemberMissionContributions
      );
    }

    return new ApiResponse<>(HttpStatus.OK.value(), groupMissionProgressResponse);
  }

  @PatchMapping("/group-mission-progress/{group_mission_progress_id}/claim")
  @Operation(summary = "Claim Group Mission reward")
  public ApiResponse<MessageResponse> claimGroupMissionReward(@PathVariable("group_mission_progress_id") String groupMissionProgressId) {
    groupMissionProgressService.claimGroupMission(groupMissionProgressId);
    return new ApiResponse<>(
        HttpStatus.OK.value(),
        new MessageResponse("Rewards claimed successfully")
    );
  }
}
