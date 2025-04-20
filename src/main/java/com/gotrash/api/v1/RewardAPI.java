package com.gotrash.api.v1;

import com.gotrash.api.response.MessageResponse;
import com.gotrash.api.v1.model.Notification;
import com.gotrash.api.v1.model.Reward;
import com.gotrash.api.v1.request.RewardRequest;
import com.gotrash.api.v1.response.NotificationResponse;
import com.gotrash.api.v1.response.RewardResponse;
import com.gotrash.api.v1.transformer.NotificationTransformer;
import com.gotrash.api.v1.transformer.RewardTransformer;
import com.gotrash.service.RewardService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("v1")
@Tag(name = "Reward", description = "API for reward")
public class RewardAPI {
    private final RewardService rewardService;

    @PostMapping("/rewards")
    @Operation(summary = "API to create a new reward")
    public ResponseEntity<RewardResponse> save(@RequestBody RewardRequest rewardRequest) {
        Reward reward = RewardTransformer.transformRequestToModel(rewardRequest);
        RewardResponse rewardResponse = RewardTransformer.transformModelToResponse(rewardService.save(reward));
        return new ResponseEntity<>(rewardResponse, HttpStatus.CREATED);
    }

    @GetMapping("/rewards")
    @Operation(summary = "API to get all reward data")
    public ResponseEntity<List<RewardResponse>> getExchanges() {
        List<Reward> rewards = rewardService.getRewards();
        List<RewardResponse> rewardResponses = rewards.stream()
            .map(RewardTransformer::transformModelToResponse)
            .toList();
        return new ResponseEntity<>(rewardResponses, HttpStatus.OK);
    }

    @GetMapping("/rewards/{reward_id}")
    @Operation(summary = "API to get reward by reward_id")
    public ResponseEntity<RewardResponse> getRewardByRewardId(@PathVariable("reward_id") String rewardId) {
        RewardResponse rewardResponse = RewardTransformer.transformModelToResponse(
                rewardService.getRewardByRewardId(rewardId)
        );
        return new ResponseEntity<>(rewardResponse, HttpStatus.OK);
    }

    @PatchMapping("/rewards")
    @Operation(summary = "API to update reward by reward_id")
    public ResponseEntity<RewardResponse> update(@RequestBody RewardRequest rewardRequest) {
        Reward reward = RewardTransformer.transformRequestToModel(rewardRequest);
        RewardResponse rewardResponse = RewardTransformer.transformModelToResponse(rewardService.update(reward));
        return new ResponseEntity<>(rewardResponse, HttpStatus.OK);
    }

    @DeleteMapping("/rewards/{reward_id}")
    @Operation(summary = "API to delete reward by reward_id")
    public ResponseEntity<MessageResponse> delete(@PathVariable("reward_id") String rewardId) {
        rewardService.delete(rewardId);
        String message = "Successfully delete reward with id " + rewardId;
        MessageResponse messageResponse = new MessageResponse(message);
        return new ResponseEntity<>(messageResponse, HttpStatus.OK);
    }
}
