package com.gotrash.api.v1;

import com.gotrash.api.response.ApiResponse;
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

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
@Tag(name = "Reward Category API", description = "API for reward category")
public class RewardCategoryAPI {
    
    private final RewardCategoryService rewardCategoryService;

    @PostMapping("/reward-categories")
    @Operation(summary = "API to create a new reward category")
    public ApiResponse<RewardCategoryResponse> save(@RequestBody RewardCategoryRequest rewardCategoryRequest) {
        RewardCategory rewardCategory = RewardCategoryTransformer.transformRequestToModel(rewardCategoryRequest);
        RewardCategoryResponse rewardCategoryResponse = RewardCategoryTransformer.transformModelToResponse(rewardCategoryService.save(rewardCategory));
        return new ApiResponse<>(HttpStatus.CREATED.value(), rewardCategoryResponse);
    }

    @GetMapping("/reward-categories")
    @Operation(summary = "API to get all reward category data")
    public ApiResponse<List<RewardCategoryResponse>> getRewardCategories() {
        List<RewardCategory> rewardCategories = rewardCategoryService.getRewardCategories();
        List<RewardCategoryResponse> rewardCategoryResponses = rewardCategories.stream()
            .map(RewardCategoryTransformer::transformModelToResponse)
            .toList();
        return new ApiResponse<>(HttpStatus.OK.value(), rewardCategoryResponses);
    }

    @GetMapping("/reward-categories/{reward_category_id}")
    @Operation(summary = "API to get reward category by reward category id")
    public ApiResponse<RewardCategoryResponse> getRewardCategoryByRewardCategoryId(@PathVariable("reward_category_id") String rewardCategoryId) {
        RewardCategoryResponse rewardCategoryResponse = RewardCategoryTransformer.transformModelToResponse(
                rewardCategoryService.getRewardCategoryByRewardCategoryId(rewardCategoryId)
        );
        return new ApiResponse<>(HttpStatus.OK.value(), rewardCategoryResponse);
    }

    @PatchMapping("/reward-categories/{reward_category_id}")
    @Operation(summary = "API to update reward category by reward category id")
    public ApiResponse<RewardCategoryResponse> update(@PathVariable("reward_category_id") String rewardCategoryId,
                                                         @RequestBody RewardCategoryRequest rewardCategoryRequest) {
        RewardCategory rewardCategory = RewardCategoryTransformer.transformRequestToModel(rewardCategoryId, rewardCategoryRequest);
        RewardCategoryResponse rewardCategoryResponse = RewardCategoryTransformer.transformModelToResponse(rewardCategoryService.update(rewardCategory));
        return new ApiResponse<>(HttpStatus.CREATED.value(), rewardCategoryResponse);
    }

    @DeleteMapping("/reward-categories/{reward_category_id}")
    @Operation(summary = "API to delete reward category by reward category id")
    public ApiResponse<MessageResponse> delete(@PathVariable("reward_category_id") String rewardCategoryId) {
        rewardCategoryService.delete(rewardCategoryId);
        String message = "Successfully delete reward category with id " + rewardCategoryId;
        return new ApiResponse<>(HttpStatus.OK.value(), message);
    }
}
