package com.gotrash.api.v1;

import com.gotrash.api.response.ApiResponse;
import com.gotrash.api.response.MessageResponse;
import com.gotrash.api.v1.model.Trash;
import com.gotrash.api.v1.request.TrashRequest;
import com.gotrash.api.v1.response.TrashResponse;
import com.gotrash.api.v1.transformer.TrashTransformer;
import com.gotrash.service.TrashService;
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
@Tag(name = "Trash API", description = "API for Trash")
public class TrashAPI {
    private final TrashService trashService;

    @PostMapping("/trashes")
    @Operation(summary = "API to create a new trash")
    public ApiResponse<TrashResponse> save(@RequestBody TrashRequest trashRequest) {
        Trash trash = TrashTransformer.transformRequestToModel(trashRequest);
        TrashResponse trashResponse = TrashTransformer.transformModelToResponse(trashService.save(trash));
        return new ApiResponse<>(HttpStatus.CREATED.value(), trashResponse);
    }

    @GetMapping("/trashes")
    @Operation(summary = "API to get all trash data")
    public ApiResponse<List<TrashResponse>> getTrashes() {
        List<Trash> trashes = trashService.getTrashes();
        List<TrashResponse> trashResponses = trashes.stream()
            .map(TrashTransformer::transformModelToResponse)
            .toList();
        return new ApiResponse<>(HttpStatus.OK.value(), trashResponses);
    }

    @GetMapping("/trashes/{trash_id}")
    @Operation(summary = "API to get trash by trash id")
    public ApiResponse<TrashResponse> getTrashByTrashId(@PathVariable("trash_id") String trashId) {
        TrashResponse trashResponse = TrashTransformer.transformModelToResponse(
                trashService.getTrashByTrashId(trashId)
        );
        return new ApiResponse<>(HttpStatus.OK.value(), trashResponse);
    }

    @PatchMapping("/trashes/{trash_id}")
    @Operation(summary = "API to update trash by trash id")
    public ApiResponse<TrashResponse> update(@PathVariable("trash_id") String trashId,
                                                @RequestBody TrashRequest trashRequest) {
        Trash trash = TrashTransformer.transformRequestToModel(trashId, trashRequest);
        TrashResponse trashResponse = TrashTransformer.transformModelToResponse(trashService.update(trash));
        return new ApiResponse<>(HttpStatus.OK.value(), trashResponse);
    }

    @DeleteMapping("/trashes/{trash_id}")
    @Operation(summary = "API to delete trash by trash id")
    public ApiResponse<MessageResponse> delete(@PathVariable("trash_id") String trashId) {
        trashService.delete(trashId);
        String message = "Successfully delete trash with id " + trashId;
        return new ApiResponse<>(HttpStatus.OK.value(), message);
    }
}
