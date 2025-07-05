package com.gotrash.api.v1;


import com.gotrash.api.response.ApiResponse;
import com.gotrash.api.response.MessageResponse;
import com.gotrash.api.v1.model.Mission;
import com.gotrash.api.v1.request.MissionRequest;
import com.gotrash.api.v1.request.MissionUpdateRequest;
import com.gotrash.api.v1.response.MissionResponse;
import com.gotrash.api.v1.transformer.MissionTransformer;
import com.gotrash.service.MissionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
@Tag(name = "Mission API", description = "API for mission")
public class MissionAPI {

  private final MissionService missionService;

  @PostMapping("/missions")
  @Operation(summary = "API to create a new mission")
  public ApiResponse<MissionResponse> save(@RequestBody MissionRequest missionRequest) {
    Mission mission = MissionTransformer.transformRequestToModel(missionRequest);
    MissionResponse missionResponse = MissionTransformer.transformModelToResponse(
        missionService.save(mission)
    );
    return new ApiResponse<>(HttpStatus.CREATED.value(), missionResponse);
  }

  @GetMapping("/missions")
  @Operation(summary = "API to get all mission data")
  public ApiResponse<List<MissionResponse>> getAllMission() {

    List<Mission> missions = missionService.getAllMission();

    List<MissionResponse> missionResponses = missions.stream()
        .map(MissionTransformer::transformModelToResponse)
        .toList();
    return new ApiResponse<>(HttpStatus.OK.value(), missionResponses);
  }

  @GetMapping("/missions/by-goal-type")
  @Operation(summary = "API to get all mission data by goal type")
  public ApiResponse<List<MissionResponse>> getAllMissionByGoalType(@RequestParam(name = "goalType", required = false) String goalType) {

    List<Mission> missions = missionService.getAllMissionFilterByMissionGoalType(goalType);

    List<MissionResponse> missionResponses = missions.stream()
        .map(MissionTransformer::transformModelToResponse)
        .toList();
    return new ApiResponse<>(HttpStatus.OK.value(), missionResponses);
  }

  @GetMapping("/missions/by-type")
  @Operation(summary = "API to get all mission data by type")
  public ApiResponse<List<MissionResponse>> getAllMissionByType(@RequestParam(name = "type") String type) {

    List<Mission> missions = missionService.getAllMissionFilterByMissionType(type);

    List<MissionResponse> missionResponses = missions.stream()
        .map(MissionTransformer::transformModelToResponse)
        .toList();
    return new ApiResponse<>(HttpStatus.OK.value(), missionResponses);
  }

  @GetMapping("/missions/{mission_id}")
  @Operation(summary = "API to get mission by mission id")
  public ApiResponse<MissionResponse> getMissionByMissionId(@PathVariable("mission_id") String missionId) {
    Mission mission = missionService.getMissionById(missionId); // You'll need to add this method to your service
    MissionResponse missionResponse = MissionTransformer.transformModelToResponse(mission);
    return new ApiResponse<>(HttpStatus.OK.value(), missionResponse);
  }

  @PatchMapping("/missions/{mission_id}")
  @Operation(summary = "API to update mission by mission id")
  public ApiResponse<MissionResponse> update(
      @PathVariable("mission_id") String missionId,
      @RequestBody MissionUpdateRequest missionUpdateRequest) {

    Mission mission = MissionTransformer.transformRequestToModel(missionId, missionUpdateRequest);
    MissionResponse missionResponse = MissionTransformer.transformModelToResponse(missionService.update(mission));
    return new ApiResponse<>(HttpStatus.OK.value(), missionResponse);
  }

  @DeleteMapping("/missions/{mission_id}")
  @Operation(summary = "API to delete mission by mission id")
  public ApiResponse<MessageResponse> delete(@PathVariable("mission_id") String missionId) {
    missionService.delete(missionId);
    String message = "Successfully delete mission with id " + missionId;
    MessageResponse messageResponse = new MessageResponse(message);
    return new ApiResponse<>(HttpStatus.OK.value(), messageResponse);
  }

}
