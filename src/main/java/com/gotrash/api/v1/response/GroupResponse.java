package com.gotrash.api.v1.response;

import com.gotrash.api.v1.model.Reward;
import com.gotrash.entity.RewardEntity;
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
public class GroupResponse {
    private String groupId;
    private Reward reward;
    private String name;
    private BigInteger coin;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
