package com.gotrash.api.v1;

import com.gotrash.api.response.MessageResponse;
import com.gotrash.api.v1.model.RewardCategory;
import com.gotrash.api.v1.request.RewardCategoryRequest;
import com.gotrash.api.v1.response.RewardCategoryResponse;
import com.gotrash.api.v1.transformer.RewardCategoryTransformer;
import com.gotrash.service.RewardCategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("v1")
@Tag(name = "Reward Category", description = "API for reward category")
public class RewardCategoryAPI {
    
    private final RewardCategoryService rewardCategoryService;

    @PostMapping("/reward-categories")
    @Operation(summary = "API to create a new reward category")
    public ResponseEntity<RewardCategoryResponse> save(@RequestBody RewardCategoryRequest rewardCategoryRequest) {
        RewardCategory rewardCategory = RewardCategoryTransformer.transformRequestToModel(rewardCategoryRequest);
        RewardCategoryResponse rewardCategoryResponse = RewardCategoryTransformer.transformModelToResponse(rewardCategoryService.save(rewardCategory));
        return new ResponseEntity<>(rewardCategoryResponse, HttpStatus.CREATED);
    }

    @GetMapping("/reward-categories/{reward_category_id}")
    @Operation(summary = "API to get reward category by reward_category_id")
    public ResponseEntity<RewardCategoryResponse> getRewardCategoryByRewardCategoryId(@PathVariable("reward_category_id") String rewardCategoryId) {
        RewardCategoryResponse rewardCategoryResponse = RewardCategoryTransformer.transformModelToResponse(
                rewardCategoryService.getRewardCategoryByRewardCategoryId(rewardCategoryId)
        );
        return new ResponseEntity<>(rewardCategoryResponse, HttpStatus.OK);
    }

    @PatchMapping("/reward-categories")
    @Operation(summary = "API to update reward category by reward_category_id")
    public ResponseEntity<RewardCategoryResponse> update(@RequestBody RewardCategoryRequest rewardCategoryRequest) {
        RewardCategory rewardCategory = RewardCategoryTransformer.transformRequestToModel(rewardCategoryRequest);
        RewardCategoryResponse rewardCategoryResponse = RewardCategoryTransformer.transformModelToResponse(rewardCategoryService.update(rewardCategory));
        return new ResponseEntity<>(rewardCategoryResponse, HttpStatus.CREATED);
    }

    @DeleteMapping("/reward-categories/{reward_category_id}")
    @Operation(summary = "API to delete reward category by reward_category_id")
    public ResponseEntity<MessageResponse> delete(@PathVariable("reward_category_id") String rewardCategoryId) {
        rewardCategoryService.delete(rewardCategoryId);
        String message = "Successfully delete reward category with id " + rewardCategoryId;
        return new ResponseEntity(message, HttpStatus.OK);
    }
}
