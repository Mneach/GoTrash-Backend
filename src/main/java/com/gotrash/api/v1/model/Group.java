package com.gotrash.api.v1.model;

import com.gotrash.entity.RewardEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Group {
    private String groupId;
    private Reward reward;
    private User owner;
    private List<GroupMember> groupMembers;
    private String name;
    private BigInteger coin;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
