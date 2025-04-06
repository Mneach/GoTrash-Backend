package com.gotrash.api.v1;

import com.gotrash.api.response.MessageResponse;
import com.gotrash.api.v1.model.TrashBin;
import com.gotrash.api.v1.request.TrashBinRequest;
import com.gotrash.api.v1.response.TrashBinResponse;
import com.gotrash.api.v1.transformer.TrashBinTransformer;
import com.gotrash.service.TrashBinService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("v1")
public class TrashBinAPI {
    private final TrashBinService trashBinService;

    @PostMapping("/trash-bin")
    public ResponseEntity<TrashBinResponse> save(@RequestBody TrashBinRequest trashBinRequest) {
        TrashBin trashBin = TrashBinTransformer.transformRequestToModel(trashBinRequest);
        TrashBinResponse trashBinResponse = TrashBinTransformer.transformModelToResponse(trashBinService.save(trashBin));
        return new ResponseEntity<>(trashBinResponse, HttpStatus.CREATED);
    }

    @GetMapping("/trash-bin/{trash_bin_id}")
    public ResponseEntity<TrashBinResponse> getTrashBinByTrashBinId(@PathVariable("trash_bin_id") String trashBinId) {
        TrashBinResponse trashBinResponse = TrashBinTransformer.transformModelToResponse(
                trashBinService.getTrashBinByTrashBinId(trashBinId)
        );
        return new ResponseEntity<>(trashBinResponse, HttpStatus.OK);
    }

    @PatchMapping("/trash-bin")
    public ResponseEntity<TrashBinResponse> update(@RequestBody TrashBinRequest trashBinRequest) {
        TrashBin TrashBin = TrashBinTransformer.transformRequestToModel(trashBinRequest);
        TrashBinResponse trashBinResponse = TrashBinTransformer.transformModelToResponse(trashBinService.save(TrashBin));
        return new ResponseEntity<>(trashBinResponse, HttpStatus.OK);
    }

    @DeleteMapping("/trash-bin/{trash_bin_id}")
    public ResponseEntity<MessageResponse> delete(@PathVariable("trash_bin_id") String trashBinId) {
        trashBinService.delete(trashBinId);
        String message = "Successfully delete trash category with id " + trashBinId;
        return new ResponseEntity(message, HttpStatus.OK);
    }
}
