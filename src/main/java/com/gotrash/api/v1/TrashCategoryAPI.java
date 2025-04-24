package com.gotrash.api.v1;

import com.gotrash.api.response.MessageResponse;
import com.gotrash.api.v1.model.TrashBin;
import com.gotrash.api.v1.model.TrashCategory;
import com.gotrash.api.v1.request.TrashCategoryRequest;
import com.gotrash.api.v1.response.TrashBinResponse;
import com.gotrash.api.v1.response.TrashCategoryResponse;
import com.gotrash.api.v1.transformer.TrashBinTransformer;
import com.gotrash.api.v1.transformer.TrashCategoryTransformer;
import com.gotrash.service.TrashCategoryService;
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
@Tag(name = "Trash Category API", description = "API for Trash Category")
public class TrashCategoryAPI {
    private final TrashCategoryService trashCategoryService;

    @PostMapping("/trash-categories")
    @Operation(summary = "API to create a new trash category")
    public ResponseEntity<TrashCategoryResponse> save(@RequestBody TrashCategoryRequest trashCategoryRequest) {
        TrashCategory trashCategory = TrashCategoryTransformer.transformRequestToModel(trashCategoryRequest);
        TrashCategoryResponse trashCategoryResponse = TrashCategoryTransformer.transformModelToResponse(trashCategoryService.save(trashCategory));
        return new ResponseEntity<>(trashCategoryResponse, HttpStatus.CREATED);
    }

    @GetMapping("/trash-categories")
    @Operation(summary = "API to get all trash category data")
    public ResponseEntity<List<TrashCategoryResponse>> getTrashCategories() {
        List<TrashCategory> trashCategories = trashCategoryService.getTrashCategories();
        List<TrashCategoryResponse> trashCategoryResponses = trashCategories.stream()
            .map(TrashCategoryTransformer::transformModelToResponse)
            .toList();
        return new ResponseEntity<>(trashCategoryResponses, HttpStatus.OK);
    }

    @GetMapping("/trash-categories/{trash_category_id}")
    @Operation(summary = "API to get trash category by trash category id")
    public ResponseEntity<TrashCategoryResponse> getTrashCategoryByTrashCategoryId(@PathVariable("trash_category_id") String trashCategoryId) {
        TrashCategoryResponse trashCategoryResponse = TrashCategoryTransformer.transformModelToResponse(
                trashCategoryService.getTrashCategoryByTrashCategoryId(trashCategoryId)
        );
        return new ResponseEntity<>(trashCategoryResponse, HttpStatus.OK);
    }

    @PatchMapping("/trash-categories/{trash_category_id}")
    @Operation(summary = "API to update trash category")
    public ResponseEntity<TrashCategoryResponse> update(@PathVariable("trash_category_id") String trashCategoryId,
                                                        @RequestBody TrashCategoryRequest trashCategoryRequest) {
        TrashCategory trashCategory = TrashCategoryTransformer.transformRequestToModel(trashCategoryId, trashCategoryRequest);
        TrashCategoryResponse trashCategoryResponse = TrashCategoryTransformer.transformModelToResponse(trashCategoryService.update(trashCategory));
        return new ResponseEntity<>(trashCategoryResponse, HttpStatus.CREATED);
    }

    @DeleteMapping("/trash-categories/{trash_category_id}")
    @Operation(summary = "API to delete trash category by trash category id")
    public ResponseEntity<MessageResponse> delete(@PathVariable("trash_category_id") String trashCategoryId) {
        trashCategoryService.delete(trashCategoryId);
        String message = "Successfully delete trash category with id " + trashCategoryId;
        return new ResponseEntity(message, HttpStatus.OK);
    }
}
