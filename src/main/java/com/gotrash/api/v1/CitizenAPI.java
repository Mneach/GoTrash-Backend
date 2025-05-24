package com.gotrash.api.v1;

import com.gotrash.api.response.ApiResponse;
import com.gotrash.api.response.MessageResponse;
import com.gotrash.api.v1.model.Citizen;
import com.gotrash.api.v1.model.Group;
import com.gotrash.api.v1.model.trashhistory.TrashHistory;
import com.gotrash.api.v1.model.streak.Streak;
import com.gotrash.api.v1.request.CitizenRequest;
import com.gotrash.api.v1.response.CitizenResponse;
import com.gotrash.api.v1.response.streak.StreakResponse;
import com.gotrash.api.v1.transformer.CitizenTransformer;
import com.gotrash.api.v1.transformer.StreakTransformer;
import com.gotrash.service.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
@Tag(name = "Citizen API", description = "API for Citizen")
public class CitizenAPI {

  private final CitizenService citizenService;
  private final TrashHistoryService trashHistoryService;
  private final GroupService groupService;
  private final StreakService streakService;

  @GetMapping("/citizens")
  @Operation(summary = "API to get all citizen data")
  public ApiResponse<List<CitizenResponse>> getCitizens() {
    List<Citizen> citizens = citizenService.getCitizens();
    List<CitizenResponse> citizenResponses = citizens.stream()
        .map(citizen -> {
          List<Group> groups = groupService.getGroupsFilterByUserId(citizen.getUserId());
          List<TrashHistory> trashHistories = trashHistoryService.getTrashHistoryByUserId(citizen.getUserId());
          return CitizenTransformer.transformModelToResponse(
              citizen,
              trashHistories,
              groups
          );
        })
        .toList();
    return new ApiResponse<>(HttpStatus.OK.value(), citizenResponses);
  }

  @GetMapping("/citizens/me")
  @Operation(summary = "API to get current citizen user")
  public ApiResponse<CitizenResponse> getMe() {
    Citizen citizen = citizenService.getMe();
    List<Group> groups = groupService.getGroupsFilterByUserId(citizen.getUserId());
    List<TrashHistory> trashHistories = trashHistoryService.getTrashHistoryByUserId(citizen.getUserId());
    CitizenResponse citizenResponse = CitizenTransformer.transformModelToResponse(
        citizen,
        trashHistories,
        groups
    );
    return new ApiResponse<>(HttpStatus.OK.value(), citizenResponse);
  }

  @GetMapping("/citizens/{user_id}")
  @Operation(summary = "API to get citizen by user id")
  public ApiResponse<CitizenResponse> getCitizenByUserId(@PathVariable("user_id") String userId) {
    Citizen citizen = citizenService.getCitizenByUserId(userId);
    List<Group> groups = groupService.getGroupsFilterByUserId(userId);
    List<TrashHistory> trashHistories = trashHistoryService.getTrashHistoryByUserId(userId);
    CitizenResponse citizenResponse = CitizenTransformer.transformModelToResponse(
        citizen,
        trashHistories,
        groups
    );
    return new ApiResponse<>(HttpStatus.OK.value(), citizenResponse);
  }

  @PatchMapping(value = "/citizens/{user_id}", consumes = {"multipart/form-data"})
  @Operation(summary = "API to update citizen")
  public ApiResponse<CitizenResponse> update(@PathVariable("user_id") String userId,
                                             @ModelAttribute CitizenRequest citizenRequest) {
    Citizen citizen = CitizenTransformer.transformRequestToModel(userId, citizenRequest);
    citizen = citizenService.update(citizen, citizenRequest.getImageFile());
    List<Group> groups = groupService.getGroupsFilterByUserId(userId);
    List<TrashHistory> trashHistories = trashHistoryService.getTrashHistoryByUserId(userId);
    CitizenResponse citizenResponse = CitizenTransformer.transformModelToResponse(
        citizen,
        trashHistories,
        groups
    );
    return new ApiResponse<>(HttpStatus.OK.value(), citizenResponse);
  }

  @DeleteMapping("/citizens/{user_id}")
  @Operation(summary = "API to delete citizen by user id")
  public ApiResponse<MessageResponse> delete(@PathVariable("user_id") String userId) {
    citizenService.delete(userId);
    String message = "Successfully delete citizen with id " + userId;
    return new ApiResponse<>(HttpStatus.OK.value(), message);
  }

  @GetMapping("/citizens/{user_id}/streak")
  @Operation(summary = "API to get citizen streak by user id")
  public ApiResponse<List<StreakResponse>> getCitizenStreak(@PathVariable("user_id") String userId) {
    List<Streak> streaks = streakService.getDailyStreaks(userId);

    List<StreakResponse> streakResponses = streaks.stream()
        .map(StreakTransformer::transformModelToResponse)
        .toList();

    return new ApiResponse<>(HttpStatus.OK.value(), streakResponses);
  }
}
