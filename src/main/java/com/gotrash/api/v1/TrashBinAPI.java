package com.gotrash.api.v1;

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

@RestController
@RequiredArgsConstructor
@RequestMapping("v1")
@Tag(name = "Trash Bin", description = "API for Trash Bin")
public class TrashBinAPI {
    private final TrashBinService trashBinService;

    @PostMapping("/trash-bins")
    @Operation(summary = "API to create a new trash bin")
    public ResponseEntity<TrashBinResponse> save(@RequestBody TrashBinRequest trashBinRequest) {
        TrashBin trashBin = TrashBinTransformer.transformRequestToModel(trashBinRequest);
        TrashBinResponse trashBinResponse = TrashBinTransformer.transformModelToResponse(trashBinService.save(trashBin));
        return new ResponseEntity<>(trashBinResponse, HttpStatus.CREATED);
    }

    @GetMapping("/trash-bins/{trash_bin_id}")
    @Operation(summary = "API to get trash bin by trash_bin_id")
    public ResponseEntity<TrashBinResponse> getTrashBinByTrashBinId(@PathVariable("trash_bin_id") String trashBinId) {
        TrashBinResponse trashBinResponse = TrashBinTransformer.transformModelToResponse(
                trashBinService.getTrashBinByTrashBinId(trashBinId)
        );
        return new ResponseEntity<>(trashBinResponse, HttpStatus.OK);
    }

    @PatchMapping("/trash-bins")
    @Operation(summary = "API to update trash bin")
    public ResponseEntity<TrashBinResponse> update(@RequestBody TrashBinRequest trashBinRequest) {
        TrashBin trashBin = TrashBinTransformer.transformRequestToModel(trashBinRequest);
        TrashBinResponse trashBinResponse = TrashBinTransformer.transformModelToResponse(trashBinService.update(trashBin));
        return new ResponseEntity<>(trashBinResponse, HttpStatus.OK);
    }

    @DeleteMapping("/trash-bins/{trash_bin_id}")
    @Operation(summary = "API to delete trash bin by trash_bin_id")
    public ResponseEntity<MessageResponse> delete(@PathVariable("trash_bin_id") String trashBinId) {
        trashBinService.delete(trashBinId);
        String message = "Successfully delete trash category with id " + trashBinId;
        return new ResponseEntity(message, HttpStatus.OK);
    }
}
