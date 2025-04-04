package com.gotrash.repository.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Table(name = "group", schema = "gotrash")
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GroupEntity {
  @Id
  @Column(updatable = false, nullable = false)
  private String id;

  @OneToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "reward_id")
  private RewardEntity reward;

  @CreationTimestamp
  @Column(updatable = false)
  private LocalDateTime createdAt;

  @UpdateTimestamp
  private LocalDateTime updatedAt;
}
