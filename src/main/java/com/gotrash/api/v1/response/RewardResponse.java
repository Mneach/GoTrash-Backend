package com.gotrash.api.v1.response;

import com.gotrash.api.v1.model.RewardCategory;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigInteger;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RewardResponse {
    private String rewardId;
    private RewardCategoryResponse rewardCategory;
    private String name;
    private BigInteger coin;
    private Integer stock;
    private String description;
    private String imageName;
    private String imageUrl;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
