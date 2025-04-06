package com.gotrash.api.v1;

import com.gotrash.api.response.MessageResponse;
import com.gotrash.api.v1.model.TrashCategory;
import com.gotrash.api.v1.request.TrashCategoryRequest;
import com.gotrash.api.v1.response.TrashCategoryResponse;
import com.gotrash.api.v1.transformer.TrashCategoryTransformer;
import com.gotrash.service.TrashCategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("v1")
public class TrashCategoryAPI {
    private final TrashCategoryService trashCategoryService;

    @PostMapping("/trash-category")
    public ResponseEntity<TrashCategoryResponse> save(@RequestBody TrashCategoryRequest trashCategoryRequest) {
        TrashCategory trashCategory = TrashCategoryTransformer.transformRequestToModel(trashCategoryRequest);
        TrashCategoryResponse trashCategoryResponse = TrashCategoryTransformer.transformModelToResponse(trashCategoryService.save(trashCategory));
        return new ResponseEntity<>(trashCategoryResponse, HttpStatus.CREATED);
    }

    @GetMapping("/trash-category/{trash_category_id}")
    public ResponseEntity<TrashCategoryResponse> getTrashCategoryByTrashCategoryId(@PathVariable("trash_category_id") String trashCategoryId) {
        TrashCategoryResponse trashCategoryResponse = TrashCategoryTransformer.transformModelToResponse(
                trashCategoryService.getTrashCategoryByTrashCategoryId(trashCategoryId)
        );
        return new ResponseEntity<>(trashCategoryResponse, HttpStatus.OK);
    }

    @PatchMapping("/trash-category")
    public ResponseEntity<TrashCategoryResponse> update(@RequestBody TrashCategoryRequest trashCategoryRequest) {
        TrashCategory TrashCategory = TrashCategoryTransformer.transformRequestToModel(trashCategoryRequest);
        TrashCategoryResponse trashCategoryResponse = TrashCategoryTransformer.transformModelToResponse(trashCategoryService.save(TrashCategory));
        return new ResponseEntity<>(trashCategoryResponse, HttpStatus.CREATED);
    }

    @DeleteMapping("/trash-category/{trash_category_id}")
    public ResponseEntity<MessageResponse> delete(@PathVariable("trash_category_id") String trashCategoryId) {
        trashCategoryService.delete(trashCategoryId);
        String message = "Successfully delete trash category with id " + trashCategoryId;
        return new ResponseEntity(message, HttpStatus.OK);
    }
}
