package com.gotrash.api.v1.request.group;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigInteger;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GroupRequest {
    private String rewardId;
    private String citizenId;
    private String name;
    private BigInteger coin;
}
