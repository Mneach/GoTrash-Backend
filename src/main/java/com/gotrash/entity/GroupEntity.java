package com.gotrash.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
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

@Table(name = "groups", schema = "gotrash")
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GroupEntity {
  @Id
  @Column(updatable = false, nullable = false, columnDefinition = "UUID")
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID groupId;

  @OneToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "reward_id", nullable = false)
  private RewardEntity reward;

  @NotNull
  private String name;

  @NotNull
  private BigInteger coin;

  @ManyToOne
  @JoinColumn(name = "user_id")
  private CitizenEntity owner;

  @OneToMany(mappedBy = "group", cascade = CascadeType.ALL)
  private List<GroupMemberEntity> groupMembers;

  @CreationTimestamp
  @Column(updatable = false)
  private LocalDateTime createdAt;

  @UpdateTimestamp
  private LocalDateTime updatedAt;
}
