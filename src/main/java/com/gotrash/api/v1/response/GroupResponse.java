package com.gotrash.api.v1.response;

import com.gotrash.api.v1.model.GroupMember;
import com.gotrash.api.v1.model.Reward;
import com.gotrash.api.v1.model.User;
import com.gotrash.entity.RewardEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GroupResponse {
    private String groupId;
    private RewardResponse reward;
    private UserResponse owner;
    private List<GroupMemberResponse> groupMembers;
    private String name;
    private BigInteger coin;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
