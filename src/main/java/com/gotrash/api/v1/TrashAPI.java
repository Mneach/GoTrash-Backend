package com.gotrash.api.v1;

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
    public ResponseEntity<TrashResponse> save(@RequestBody TrashRequest trashRequest) {
        Trash trash = TrashTransformer.transformRequestToModel(trashRequest);
        TrashResponse trashResponse = TrashTransformer.transformModelToResponse(trashService.save(trash));
        return new ResponseEntity<>(trashResponse, HttpStatus.CREATED);
    }

    @GetMapping("/trashes")
    @Operation(summary = "API to get all trash data")
    public ResponseEntity<List<TrashResponse>> getTrashes() {
        List<Trash> trashes = trashService.getTrashes();
        List<TrashResponse> trashResponses = trashes.stream()
            .map(TrashTransformer::transformModelToResponse)
            .toList();
        return new ResponseEntity<>(trashResponses, HttpStatus.OK);
    }

    @GetMapping("/trashes/{trash_id}")
    @Operation(summary = "API to get trash by trash id")
    public ResponseEntity<TrashResponse> getTrashByTrashId(@PathVariable("trash_id") String trashId) {
        TrashResponse trashResponse = TrashTransformer.transformModelToResponse(
                trashService.getTrashByTrashId(trashId)
        );
        return new ResponseEntity<>(trashResponse, HttpStatus.OK);
    }

    @PatchMapping("/trashes/{trash_id}")
    @Operation(summary = "API to update trash by trash id")
    public ResponseEntity<TrashResponse> update(@PathVariable("trash_id") String trashId,
                                                @RequestBody TrashRequest trashRequest) {
        Trash trash = TrashTransformer.transformRequestToModel(trashId, trashRequest);
        TrashResponse trashResponse = TrashTransformer.transformModelToResponse(trashService.update(trash));
        return new ResponseEntity<>(trashResponse, HttpStatus.OK);
    }

    @DeleteMapping("/trashes/{trash_id}")
    @Operation(summary = "API to delete trash by trash id")
    public ResponseEntity<MessageResponse> delete(@PathVariable("trash_id") String trashId) {
        trashService.delete(trashId);
        String message = "Successfully delete trash with id " + trashId;
        return new ResponseEntity(message, HttpStatus.OK);
    }
}
