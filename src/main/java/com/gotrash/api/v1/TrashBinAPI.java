package com.gotrash.api.v1;

import com.gotrash.api.response.ApiResponse;
import com.gotrash.api.response.MessageResponse;
import com.gotrash.api.v1.model.TrashBin;
import com.gotrash.api.v1.request.TrashBinRequest;
import com.gotrash.api.v1.response.TrashBinResponse;
import com.gotrash.api.v1.transformer.TrashBinTransformer;
import com.gotrash.service.TrashBinService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
@Tag(name = "Trash Bin API", description = "API for Trash Bin")
public class TrashBinAPI {
    private final TrashBinService trashBinService;

    @PostMapping(value = "/trash-bins", consumes = {"multipart/form-data"})
    @Operation(summary = "API to create a new trash bin")
    public ApiResponse<TrashBinResponse> save(@ModelAttribute TrashBinRequest trashBinRequest) {
        TrashBin trashBin = TrashBinTransformer.transformRequestToModel(trashBinRequest);
        TrashBinResponse trashBinResponse = TrashBinTransformer.transformModelToResponse(trashBinService.save(trashBin, trashBinRequest.getImageFile()));
        return new ApiResponse<>(HttpStatus.CREATED.value(), trashBinResponse);
    }

    @GetMapping("/trash-bins")
    @Operation(summary = "API to get all trash bin data")
    public ApiResponse<List<TrashBinResponse>> getTrashBins() {
        List<TrashBin> trashBins = trashBinService.getTrashBins();
        List<TrashBinResponse> trashBinResponses = trashBins.stream()
            .map(TrashBinTransformer::transformModelToResponse)
            .toList();
        return new ApiResponse<>(HttpStatus.OK.value(), trashBinResponses);
    }

    @GetMapping("/trash-bins/wastebanks/{user_id}")
    @Operation(summary = "API to get all wastebank trash bin data")
    public ApiResponse<List<TrashBinResponse>> getTrashBinByWasteBankId(@PathVariable("user_id") String userId) {
        List<TrashBin> trashBins = trashBinService.getTrashBinByWasteBankId(userId);
        List<TrashBinResponse> trashBinResponses = trashBins.stream()
            .map(TrashBinTransformer::transformModelToResponse)
            .toList();
        return new ApiResponse<>(HttpStatus.OK.value(), trashBinResponses);
    }

    @GetMapping("/trash-bins/{trash_bin_id}")
    @Operation(summary = "API to get trash bin by trash bin id")
    public ApiResponse<TrashBinResponse> getTrashBinByTrashBinId(@PathVariable("trash_bin_id") String trashBinId) {
        TrashBinResponse trashBinResponse = TrashBinTransformer.transformModelToResponse(
                trashBinService.getTrashBinByTrashBinId(trashBinId)
        );
        return new ApiResponse<>(HttpStatus.OK.value(), trashBinResponse);
    }

    @PatchMapping(value = "/trash-bins/{trash_bin_id}", consumes = {"multipart/form-data"})
    @Operation(summary = "API to update trash bin by trash bin id")
    public ApiResponse<TrashBinResponse> update(@PathVariable("trash_bin_id") String trashBinId,
                                                @ModelAttribute TrashBinRequest trashBinRequest) {
        TrashBin trashBin = TrashBinTransformer.transformRequestToModel(trashBinId, trashBinRequest);
        TrashBinResponse trashBinResponse = TrashBinTransformer.transformModelToResponse(trashBinService.update(trashBin, trashBinRequest.getImageFile()));
        return new ApiResponse<>(HttpStatus.OK.value(), trashBinResponse);
    }

    @DeleteMapping("/trash-bins/{trash_bin_id}")
    @Operation(summary = "API to delete trash bin by trash bin id")
    public ApiResponse<MessageResponse> delete(@PathVariable("trash_bin_id") String trashBinId) {
        trashBinService.delete(trashBinId);
        String message = "Successfully delete trash category with id " + trashBinId;
        return new ApiResponse<>(HttpStatus.OK.value(), message);
    }
}
