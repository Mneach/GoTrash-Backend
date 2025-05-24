package com.gotrash.entity;

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
import java.util.UUID;

@Entity
@Table(name = "rewards", schema = "gotrash")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RewardEntity {

  @Id
  @Column(updatable = false, nullable = false, columnDefinition = "UUID")
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID rewardId;

  @OneToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "reward_category_id", nullable = false)
  private RewardCategoryEntity rewardCategory;

  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "waste_bank_id", nullable = false)
  private WasteBankEntity wasteBank;

  @NotNull
  private String name;

  @NotNull
  private BigInteger coin;

  @NotNull
  private Integer stock;

  @NotNull
  private String description;

  @NotNull
  private String imageUrl;

  @CreationTimestamp
  @Column(updatable = false)
  private LocalDateTime createdAt;

  @UpdateTimestamp
  private LocalDateTime updatedAt;
}
