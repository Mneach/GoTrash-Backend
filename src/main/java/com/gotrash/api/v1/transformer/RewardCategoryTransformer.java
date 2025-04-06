package com.gotrash.api.v1.transformer;

import com.gotrash.api.v1.model.RewardCategory;
import com.gotrash.api.v1.request.RewardCategoryRequest;
import com.gotrash.api.v1.response.RewardCategoryResponse;
import com.gotrash.entity.RewardCategoryEntity;

import java.util.UUID;

public class RewardCategoryTransformer {

    public static RewardCategory transformRequestToModel(RewardCategoryRequest rewardCategoryRequest) {
        return RewardCategory.builder()
                .rewardCategoryId(rewardCategoryRequest.getRewardCategoryId())
                .name(rewardCategoryRequest.getName())
                .build();
    }

    public static RewardCategory transformEntityToModel(RewardCategoryEntity rewardCategoryEntity) {
        return RewardCategory.builder()
                .rewardCategoryId(rewardCategoryEntity.getRewardCategoryId().toString())
                .name(rewardCategoryEntity.getName())
                .createdAt(rewardCategoryEntity.getCreatedAt())
                .updatedAt(rewardCategoryEntity.getUpdatedAt())
                .build();
    }

    public static RewardCategoryEntity transformModelToEntity(RewardCategory rewardCategory) {
        return RewardCategoryEntity.builder()
                .rewardCategoryId(rewardCategory.getRewardCategoryId() != null ? UUID.fromString(rewardCategory.getRewardCategoryId()) : null)
                .name(rewardCategory.getName())
                .createdAt(rewardCategory.getCreatedAt())
                .updatedAt(rewardCategory.getUpdatedAt())
                .build();
    }

    public static RewardCategoryResponse transformModelToResponse(RewardCategory rewardCategory) {
        return RewardCategoryResponse.builder()
                .rewardCategoryId(UUID.fromString(rewardCategory.getRewardCategoryId()))
                .name(rewardCategory.getName())
                .createdAt(rewardCategory.getCreatedAt())
                .updatedAt(rewardCategory.getUpdatedAt())
                .build();
    }
}
