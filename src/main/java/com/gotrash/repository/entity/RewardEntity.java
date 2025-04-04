package com.gotrash.repository.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
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

@Entity
@Table(name = "reward", schema = "gotrash")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RewardEntity {

  @Id
  @Column(nullable = false, updatable = false)
  private String id;

  @OneToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "reward_category_id", nullable = false)
  private RewardCategoryEntity rewardCategory;

  @NotNull
  private String name;

  @NotNull
  private BigInteger coin;

  @NotNull
  private Integer stock;

  @NotNull
  private String description;

  @NotNull
  private String imageName;

  @NotNull
  private String imageUrl;

  @CreationTimestamp
  @Column(updatable = false)
  private LocalDateTime createdAt;

  @UpdateTimestamp
  private LocalDateTime updatedAt;
}
