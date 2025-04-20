package com.gotrash.api.v1;

import com.gotrash.api.response.MessageResponse;
import com.gotrash.api.v1.model.TrashCategory;
import com.gotrash.api.v1.model.TrashHistory;
import com.gotrash.api.v1.request.TrashHistoryRequest;
import com.gotrash.api.v1.response.TrashCategoryResponse;
import com.gotrash.api.v1.response.TrashHistoryResponse;
import com.gotrash.api.v1.transformer.TrashCategoryTransformer;
import com.gotrash.api.v1.transformer.TrashHistoryTransformer;
import com.gotrash.service.TrashHistoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("v1")
@Tag(name = "Trash History", description = "API for Trash History")
public class TrashHistoryAPI {
    
    private final TrashHistoryService trashHistoryService;

    @PostMapping("/trash-histories")
    @Operation(summary = "API to create a new trash history")
    public ResponseEntity<TrashHistoryResponse> save(@RequestBody TrashHistoryRequest trashHistoryRequest) {
        TrashHistory trashHistory = TrashHistoryTransformer.transformRequestToModel(trashHistoryRequest);
        TrashHistoryResponse trashHistoryResponse = TrashHistoryTransformer.transformModelToResponse(trashHistoryService.save(trashHistory));
        return new ResponseEntity<>(trashHistoryResponse, HttpStatus.CREATED);
    }

    @GetMapping("/trash-histories")
    @Operation(summary = "API to get all trash history data")
    public ResponseEntity<List<TrashHistoryResponse>> getExchanges() {
        List<TrashHistory> trashHistories = trashHistoryService.getTrashHistories();
        List<TrashHistoryResponse> trashHistoryResponses = trashHistories.stream()
            .map(TrashHistoryTransformer::transformModelToResponse)
            .toList();
        return new ResponseEntity<>(trashHistoryResponses, HttpStatus.OK);
    }

    @GetMapping("/trash-histories/{trash_history_id}")
    @Operation(summary = "API to get trash history by trash_history_id")
    public ResponseEntity<TrashHistoryResponse> getTrashByTrashId(@PathVariable("trash_history_id") String trashHistoryId) {
        TrashHistoryResponse trashHistoryResponse = TrashHistoryTransformer.transformModelToResponse(
                trashHistoryService.getTrashHistoryByTrashHistoryId(trashHistoryId)
        );
        return new ResponseEntity<>(trashHistoryResponse, HttpStatus.OK);
    }

    @GetMapping("/trash-histories/user/{user_id}")
    @Operation(summary = "API to get trash history by user_id")
    public ResponseEntity<List<TrashHistoryResponse>> getTrashByUserId(@PathVariable("user_id") String userId) {
        List<TrashHistory> trashHistories = trashHistoryService.getTrashHistoryByUserId(userId);
        List<TrashHistoryResponse> trashHistoryResponses = trashHistories.stream()
                .map(TrashHistoryTransformer::transformModelToResponse)
                .toList();
        return new ResponseEntity<>(trashHistoryResponses, HttpStatus.OK);
    }

    @PatchMapping("/trash-histories")
    @Operation(summary = "API to update trash history")
    public ResponseEntity<TrashHistoryResponse> update(@RequestBody TrashHistoryRequest trashHistoryRequest) {
        TrashHistory trashHistory = TrashHistoryTransformer.transformRequestToModel(trashHistoryRequest);
        TrashHistoryResponse trashHistoryResponse = TrashHistoryTransformer.transformModelToResponse(trashHistoryService.update(trashHistory));
        return new ResponseEntity<>(trashHistoryResponse, HttpStatus.OK);
    }

    @DeleteMapping("/trash-histories/{trash_history_id}")
    @Operation(summary = "API to delete trash history by trash_history_id")
    public ResponseEntity<MessageResponse> delete(@PathVariable("trash_history_id") String trashHistoryId) {
        trashHistoryService.delete(trashHistoryId);
        String message = "Successfully delete trashHistory with id " + trashHistoryId;
        return new ResponseEntity(message, HttpStatus.OK);
    }
}
