package com.gotrash.api.v1;

import com.gotrash.api.v1.model.DailyMissionProgress;
import com.gotrash.api.v1.transformer.DailyMissionProgressTransformer;
import com.gotrash.api.v1.response.DailyMissionResponse;
import com.gotrash.api.response.ApiResponse;
import com.gotrash.api.response.MessageResponse;
import com.gotrash.service.DailyMissionProgressService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
@Tag(name = "Daily Mission Progress API", description = "API for Daily Mission Progress")
public class DailyMissionProgressAPI {

  private final DailyMissionProgressService dailyMissionProgressService;

  @PostMapping("daily-mission-progress/assign-daily-missions")
  @Operation(summary = "Trigger daily mission assignment manually")
  public ApiResponse<MessageResponse> triggerMissionAssignment() {
    dailyMissionProgressService.assignDailyMissionsToAllCitizens();
    return new ApiResponse<>(
        HttpStatus.OK.value(),
        new MessageResponse("Daily missions assignment triggered successfully")
    );
  }

  @GetMapping("/daily-mission-progress")
  @Operation(summary = "Get all daily missions progress")
  public ApiResponse<List<DailyMissionResponse>> getAllDailyMissionProgress() {
    List<DailyMissionProgress> progresses = dailyMissionProgressService.getAllDailyMissionProgress();
    List<DailyMissionResponse> responses = progresses.stream()
        .map(DailyMissionProgressTransformer::transformModelToResponse)
        .toList();
    return new ApiResponse<>(HttpStatus.OK.value(), responses);
  }

  @GetMapping("/daily-mission-progress/citizen/{citizen_id}")
  @Operation(summary = "Get all daily missions progress by citizen ID")
  public ApiResponse<List<DailyMissionResponse>> getDailyMissionsByCitizen(
      @PathVariable("citizen_id") String citizenId) {
    List<DailyMissionProgress> progresses = dailyMissionProgressService.getAllDailyMissionProgressByCitizenId(citizenId);
    List<DailyMissionResponse> responses = progresses.stream()
        .map(DailyMissionProgressTransformer::transformModelToResponse)
        .toList();
    return new ApiResponse<>(HttpStatus.OK.value(), responses);
  }

  @GetMapping("/daily-mission-progress/citizen/{citizen_id}/active")
  @Operation(summary = "Get all active daily missions progress by citizen ID")
  public ApiResponse<List<DailyMissionResponse>> getActiveDailyMissionsByCitizen(
      @PathVariable("citizen_id") String citizenId) {
    List<DailyMissionProgress> progresses = dailyMissionProgressService.getActiveDailyMissionProgressByCitizenId(citizenId);
    List<DailyMissionResponse> responses = progresses.stream()
        .map(DailyMissionProgressTransformer::transformModelToResponse)
        .toList();
    return new ApiResponse<>(HttpStatus.OK.value(), responses);
  }

  @PatchMapping("/daily-mission-progress/{daily_mission_progress_id}/claim")
  @Operation(summary = "Claim daily mission reward")
  public ApiResponse<DailyMissionResponse> claimReward(
      @PathVariable("daily_mission_progress_id") String progressId) {
    DailyMissionResponse response = DailyMissionProgressTransformer.transformModelToResponse(
        dailyMissionProgressService.claimDailyMissionReward(progressId));
    return new ApiResponse<>(HttpStatus.OK.value(), response);
  }
}
