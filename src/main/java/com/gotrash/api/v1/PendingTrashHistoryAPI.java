package com.gotrash.api.v1;

import com.gotrash.api.response.ApiResponse;
import com.gotrash.api.response.MessageResponse;
import com.gotrash.api.v1.model.pendingtrashhistory.PendingTrashHistory;
import com.gotrash.api.v1.request.PendingTrashHistoryRequest;
import com.gotrash.api.v1.response.pendingtrashhistory.ClaimPendingTrashHistoryResponse;
import com.gotrash.api.v1.response.pendingtrashhistory.PendingTrashHistoryResponse;
import com.gotrash.api.v1.transformer.pendingtrashhistory.ClaimPendingTrashHistoryTransformer;
import com.gotrash.api.v1.transformer.pendingtrashhistory.PendingTrashHistoryTransformer;
import com.gotrash.service.PendingTrashHistoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
@Tag(name = "Pending Trash History API", description = "API for Pending Trash History")
public class PendingTrashHistoryAPI {

  private final PendingTrashHistoryService pendingTrashHistoryService;

  @PostMapping("/pending-trash-histories")
  @Operation(summary = "API to create a new pending trash history")
  public ApiResponse<MessageResponse> save(@RequestBody PendingTrashHistoryRequest pendingTrashHistoryRequest) {
    PendingTrashHistory pendingTrashHistory = PendingTrashHistoryTransformer.transformRequestToModel(pendingTrashHistoryRequest);
    pendingTrashHistoryService.save(pendingTrashHistory);

    MessageResponse messageResponse = new MessageResponse("Successfully save pending trash history data");
    return new ApiResponse<>(HttpStatus.CREATED.value(), messageResponse);
  }

  @GetMapping("/pending-trash-histories/trash-bin/{trash_bin_id}")
  @Operation(summary = "API to get all pending trash history data by trash bin id")
  public ApiResponse<List<PendingTrashHistoryResponse>> getTrashHistories(@PathVariable("trash_bin_id") String trashBinId) {
    List<PendingTrashHistory> pendingTrashHistories = pendingTrashHistoryService.getPendingTrashHistoryByTrashBinId(trashBinId);
    List<PendingTrashHistoryResponse> pendingTrashHistoryResponses = pendingTrashHistories.stream()
        .map(PendingTrashHistoryTransformer::transformModelToResponse)
        .toList();

    return new ApiResponse<>(HttpStatus.OK.value(), pendingTrashHistoryResponses);
  }

  @PutMapping("/pending-trash-histories/trash-bin/{trash_bin_id}/claim/{citizen_id}")
  @Operation(summary = "API to get claim pending trash history by trash bin id")
  public ApiResponse<ClaimPendingTrashHistoryResponse> getTrashHistories(@PathVariable("trash_bin_id") String trashBinId,
                                                                               @PathVariable("citizen_id") String citizenId) {

    ClaimPendingTrashHistoryResponse claimPendingTrashHistoryResponse = ClaimPendingTrashHistoryTransformer.transformModelToResponse(
        pendingTrashHistoryService.claimPendingTrashHistoryByTrashBinId(citizenId, trashBinId)
    );

    return new ApiResponse<>(HttpStatus.OK.value(), claimPendingTrashHistoryResponse);
  }
}
