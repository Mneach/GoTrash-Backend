package com.gotrash.api.v1;

import com.gotrash.api.response.MessageResponse;
import com.gotrash.api.v1.model.Reward;
import com.gotrash.api.v1.request.RewardRequest;
import com.gotrash.api.v1.response.RewardResponse;
import com.gotrash.api.v1.transformer.RewardTransformer;
import com.gotrash.service.RewardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("v1")
public class RewardAPI {
    private final RewardService rewardService;

    @PostMapping("/reward")
    public ResponseEntity<RewardResponse> save(@RequestBody RewardRequest rewardRequest) {
        Reward reward = RewardTransformer.transformRequestToModel(rewardRequest);
        RewardResponse rewardResponse = RewardTransformer.transformModelToResponse(rewardService.save(reward));
        return new ResponseEntity<>(rewardResponse, HttpStatus.CREATED);
    }

    @GetMapping("/reward/{reward_id}")
    public ResponseEntity<RewardResponse> getRewardByRewardId(@PathVariable("reward_id") String rewardId) {
        RewardResponse rewardResponse = RewardTransformer.transformModelToResponse(
                rewardService.getRewardByRewardId(rewardId)
        );
        return new ResponseEntity<>(rewardResponse, HttpStatus.OK);
    }

    @PatchMapping("/reward")
    public ResponseEntity<RewardResponse> update(@RequestBody RewardRequest rewardRequest) {
        Reward reward = RewardTransformer.transformRequestToModel(rewardRequest);
        RewardResponse rewardResponse = RewardTransformer.transformModelToResponse(rewardService.update(reward));
        return new ResponseEntity<>(rewardResponse, HttpStatus.OK);
    }

    @DeleteMapping("/reward/{reward_id}")
    public ResponseEntity<MessageResponse> delete(@PathVariable("reward_id") String rewardId) {
        rewardService.delete(rewardId);
        String message = "Successfully delete reward with id " + rewardId;
        MessageResponse messageResponse = new MessageResponse(message);
        return new ResponseEntity<>(messageResponse, HttpStatus.OK);
    }
}
