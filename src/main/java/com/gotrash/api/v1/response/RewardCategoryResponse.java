package com.gotrash.api.v1.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RewardCategoryResponse {
    private UUID rewardCategoryId;
    private String name;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
