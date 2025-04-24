package com.gotrash.api.v1.transformer;

import com.gotrash.api.v1.model.Reward;
import com.gotrash.api.v1.model.RewardCategory;
import com.gotrash.api.v1.request.RewardRequest;
import com.gotrash.api.v1.response.RewardResponse;
import com.gotrash.entity.RewardEntity;

import java.util.UUID;

public class RewardTransformer {
    public static Reward transformEntityToModel(RewardEntity rewardEntity) {
        return Reward.builder()
                .rewardId(rewardEntity.getRewardId().toString())
                .rewardCategory(RewardCategoryTransformer.transformEntityToModel(rewardEntity.getRewardCategory()))
                .name(rewardEntity.getName())
                .coin(rewardEntity.getCoin())
                .stock(rewardEntity.getStock())
                .description(rewardEntity.getDescription())
                .imageName(rewardEntity.getImageName())
                .imageUrl(rewardEntity.getImageUrl())
                .createdAt(rewardEntity.getCreatedAt())
                .updatedAt(rewardEntity.getUpdatedAt())
                .build();
    }

    public static RewardEntity transformModelToEntity(Reward reward) {
        return RewardEntity.builder()
                .rewardId(reward.getRewardId() != null ? UUID.fromString(reward.getRewardId()) : null)
                .rewardCategory(RewardCategoryTransformer.transformModelToEntity(reward.getRewardCategory()))
                .name(reward.getName())
                .coin(reward.getCoin())
                .stock(reward.getStock())
                .description(reward.getDescription())
                .imageName(reward.getImageName())
                .imageUrl(reward.getImageUrl())
                .build();
    }

    public static Reward transformRequestToModel(String rewardId, RewardRequest rewardRequest) {
        return Reward.builder()
            .rewardId(rewardId)
            .rewardCategory(RewardCategory.builder().rewardCategoryId(rewardRequest.getRewardCategoryId()).build())
            .name(rewardRequest.getName())
            .coin(rewardRequest.getCoin())
            .stock(rewardRequest.getStock())
            .description(rewardRequest.getDescription())
            .imageName(rewardRequest.getImageName())
            .imageUrl(rewardRequest.getImageUrl())
            .build();
    }

    public static Reward transformRequestToModel(RewardRequest rewardRequest) {
        return Reward.builder()
                .rewardCategory(RewardCategory.builder().rewardCategoryId(rewardRequest.getRewardCategoryId()).build())
                .name(rewardRequest.getName())
                .coin(rewardRequest.getCoin())
                .stock(rewardRequest.getStock())
                .description(rewardRequest.getDescription())
                .imageName(rewardRequest.getImageName())
                .imageUrl(rewardRequest.getImageUrl())
                .build();
    }

    public static RewardResponse transformModelToResponse(Reward reward) {
        return RewardResponse.builder()
                .rewardId(reward.getRewardId())
                .rewardCategory(RewardCategoryTransformer.transformModelToResponse(reward.getRewardCategory()))
                .name(reward.getName())
                .coin(reward.getCoin())
                .stock(reward.getStock())
                .description(reward.getDescription())
                .imageName(reward.getImageName())
                .imageUrl(reward.getImageUrl())
                .createdAt(reward.getCreatedAt())
                .updatedAt(reward.getUpdatedAt())
                .build();
    }
}
