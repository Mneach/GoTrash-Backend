package com.gotrash.api.v1;

import com.gotrash.api.response.ApiResponse;
import com.gotrash.api.response.MessageResponse;
import com.gotrash.api.v1.model.Reward;
import com.gotrash.api.v1.request.RewardRequest;
import com.gotrash.api.v1.response.RewardResponse;
import com.gotrash.api.v1.transformer.RewardTransformer;
import com.gotrash.service.RewardService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
@Tag(name = "Reward API", description = "API for reward")
public class RewardAPI {
    private final RewardService rewardService;

    @PostMapping(value = "/rewards", consumes = {"multipart/form-data"})
    @Operation(summary = "API to create a new reward")
    public ApiResponse<RewardResponse> save(@ModelAttribute RewardRequest rewardRequest) {
        Reward reward = RewardTransformer.transformRequestToModel(rewardRequest);
        RewardResponse rewardResponse = RewardTransformer.transformModelToResponse(rewardService.save(reward, rewardRequest.getImageFile()));
        return new ApiResponse<>(HttpStatus.CREATED.value(), rewardResponse);
    }

    @GetMapping("/rewards")
    @Operation(summary = "API to get all reward data")
    public ApiResponse<List<RewardResponse>> getRewards() {
        List<Reward> rewards = rewardService.getRewards();
        List<RewardResponse> rewardResponses = rewards.stream()
            .map(RewardTransformer::transformModelToResponse)
            .toList();
        return new ApiResponse<>(HttpStatus.OK.value(), rewardResponses);
    }

    @GetMapping("/rewards/{reward_id}")
    @Operation(summary = "API to get reward by reward id")
    public ApiResponse<RewardResponse> getRewardByRewardId(@PathVariable("reward_id") String rewardId) {
        RewardResponse rewardResponse = RewardTransformer.transformModelToResponse(
                rewardService.getRewardByRewardId(rewardId)
        );
        return new ApiResponse<>(HttpStatus.OK.value(), rewardResponse);
    }

    @PatchMapping(value = "/rewards/{reward_id}", consumes = {"multipart/form-data"})
    @Operation(summary = "API to update reward by reward id")
    public ApiResponse<RewardResponse> update(@PathVariable("reward_id") String rewardId,
                                              @ModelAttribute RewardRequest rewardRequest) {
        Reward reward = RewardTransformer.transformRequestToModel(rewardId, rewardRequest);
        RewardResponse rewardResponse = RewardTransformer.transformModelToResponse(rewardService.update(reward, rewardRequest.getImageFile()));
        return new ApiResponse<>(HttpStatus.OK.value(), rewardResponse);
    }

    @DeleteMapping("/rewards/{reward_id}")
    @Operation(summary = "API to delete reward by reward id")
    public ApiResponse<MessageResponse> delete(@PathVariable("reward_id") String rewardId) {
        rewardService.delete(rewardId);
        String message = "Successfully delete reward with id " + rewardId;
        MessageResponse messageResponse = new MessageResponse(message);
        return new ApiResponse<>(HttpStatus.OK.value(), messageResponse);
    }
}
