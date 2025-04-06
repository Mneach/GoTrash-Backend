package com.gotrash.api.v1;

import com.gotrash.api.response.MessageResponse;
import com.gotrash.api.v1.model.TrashHistory;
import com.gotrash.api.v1.request.TrashHistoryRequest;
import com.gotrash.api.v1.response.TrashHistoryResponse;
import com.gotrash.api.v1.transformer.TrashHistoryTransformer;
import com.gotrash.service.TrashHistoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("v1")
public class TrashHistoryAPI {
    
    private final TrashHistoryService trashHistoryService;

    @PostMapping("/trash-history")
    public ResponseEntity<TrashHistoryResponse> save(@RequestBody TrashHistoryRequest trashHistoryRequest) {
        TrashHistory trashHistory = TrashHistoryTransformer.transformRequestToModel(trashHistoryRequest);
        TrashHistoryResponse trashHistoryResponse = TrashHistoryTransformer.transformModelToResponse(trashHistoryService.save(trashHistory));
        return new ResponseEntity<>(trashHistoryResponse, HttpStatus.CREATED);
    }

    @GetMapping("/trash-history/{trash_history_id}")
    public ResponseEntity<TrashHistoryResponse> getTrashByTrashId(@PathVariable("trash_history_id") String trashHistoryId) {
        TrashHistoryResponse trashHistoryResponse = TrashHistoryTransformer.transformModelToResponse(
                trashHistoryService.getTrashHistoryByTrashHistoryId(trashHistoryId)
        );
        return new ResponseEntity<>(trashHistoryResponse, HttpStatus.OK);
    }

    @GetMapping("/trash-history/user/{user_id}")
    public ResponseEntity<List<TrashHistoryResponse>> getTrashByUserId(@PathVariable("user_id") String userId) {
        List<TrashHistory> trashHistories = trashHistoryService.getTrashHistoryByUserId(userId);
        List<TrashHistoryResponse> trashHistoryResponses = trashHistories.stream()
                .map(TrashHistoryTransformer::transformModelToResponse)
                .toList();
        return new ResponseEntity<>(trashHistoryResponses, HttpStatus.OK);
    }

    @PatchMapping("/trash-history")
    public ResponseEntity<TrashHistoryResponse> update(@RequestBody TrashHistoryRequest trashHistoryRequest) {
        TrashHistory TrashHistory = TrashHistoryTransformer.transformRequestToModel(trashHistoryRequest);
        TrashHistoryResponse trashHistoryResponse = TrashHistoryTransformer.transformModelToResponse(trashHistoryService.update(TrashHistory));
        return new ResponseEntity<>(trashHistoryResponse, HttpStatus.OK);
    }

    @DeleteMapping("/trash-history/{trash_history_id}")
    public ResponseEntity<MessageResponse> delete(@PathVariable("trash_history_id") String trashHistoryId) {
        trashHistoryService.delete(trashHistoryId);
        String message = "Successfully delete trashHistory with id " + trashHistoryId;
        return new ResponseEntity(message, HttpStatus.OK);
    }
}
