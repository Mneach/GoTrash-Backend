package com.gotrash.api.v1.request;

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
public class GroupRequest {
    private String groupId;
    private String rewardId;
    private String name;
    private BigInteger coin;
}
