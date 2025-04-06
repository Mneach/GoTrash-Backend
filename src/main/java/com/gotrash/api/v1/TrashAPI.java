package com.gotrash.api.v1;

import com.gotrash.api.response.MessageResponse;
import com.gotrash.api.v1.model.Trash;
import com.gotrash.api.v1.request.TrashRequest;
import com.gotrash.api.v1.response.TrashResponse;
import com.gotrash.api.v1.transformer.TrashTransformer;
import com.gotrash.service.TrashService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("v1")
public class TrashAPI {
    private final TrashService trashService;

    @PostMapping("/trash")
    public ResponseEntity<TrashResponse> save(@RequestBody TrashRequest trashRequest) {
        Trash trash = TrashTransformer.transformRequestToModel(trashRequest);
        TrashResponse trashResponse = TrashTransformer.transformModelToResponse(trashService.save(trash));
        return new ResponseEntity<>(trashResponse, HttpStatus.CREATED);
    }

    @GetMapping("/trash/{trash_id}")
    public ResponseEntity<TrashResponse> getTrashByTrashId(@PathVariable("trash_id") String trashId) {
        TrashResponse trashResponse = TrashTransformer.transformModelToResponse(
                trashService.getTrashByTrashId(trashId)
        );
        return new ResponseEntity<>(trashResponse, HttpStatus.OK);
    }

    @PatchMapping("/trash")
    public ResponseEntity<TrashResponse> update(@RequestBody TrashRequest trashRequest) {
        Trash Trash = TrashTransformer.transformRequestToModel(trashRequest);
        TrashResponse trashResponse = TrashTransformer.transformModelToResponse(trashService.save(Trash));
        return new ResponseEntity<>(trashResponse, HttpStatus.OK);
    }

    @DeleteMapping("/trash/{trash_id}")
    public ResponseEntity<MessageResponse> delete(@PathVariable("trash_id") String trashId) {
        trashService.delete(trashId);
        String message = "Successfully delete trash with id " + trashId;
        return new ResponseEntity(message, HttpStatus.OK);
    }
}
