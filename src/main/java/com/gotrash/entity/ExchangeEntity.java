package com.gotrash.entity;

import jakarta.persistence.Column;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Table(name = "exchanges", schema = "gotrash")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ExchangeEntity {

  @Id
  @Column(updatable = false, nullable = false, columnDefinition = "UUID")
  @GeneratedValue(strategy = GenerationType.UUID)
  private String exchangeId;

  @OneToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "user_id", nullable = false)
  private UserEntity user;

  @OneToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "reward_id", nullable = false)
  private RewardEntity reward;

  @CreationTimestamp
  @Column(updatable = false)
  private LocalDateTime createdAt;

  @UpdateTimestamp
  private LocalDateTime updatedAt;
}
