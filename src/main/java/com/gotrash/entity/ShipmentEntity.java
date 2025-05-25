package com.gotrash.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
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
import java.util.UUID;

@Entity
@Table(name = "shipments", schema = "gotrash")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ShipmentEntity {

  @Id
  @Column(updatable = false, nullable = false, columnDefinition = "UUID")
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID shipmentId;

  @OneToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "citizen_id", nullable = false)
  private CitizenEntity citizen;

  @OneToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "citizen_address_id", nullable = false)
  private CitizenAddressEntity citizenAddress;

  @OneToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "reward_id", nullable = false)
  private RewardEntity reward;

  @NotNull
  private String status;

  @NotNull
  private Integer quantity;

  @CreationTimestamp
  @Column(updatable = false)
  private LocalDateTime createdAt;

  @UpdateTimestamp
  private LocalDateTime updatedAt;
}
