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

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "trash_bins", schema = "gotrash")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TrashBinEntity {
  @Id
  @Column(updatable = false, nullable = false, columnDefinition = "UUID")
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID trashBinId;

  @OneToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "waste_bank_id", nullable = false)
  private WasteBankEntity wasteBank;

  @NotNull
  private String name;

  @NotNull
  private Double latitude;

  @NotNull
  private Double longitude;

  @NotNull
  private String address;

  @NotNull
  private String imageUrl;

  @CreationTimestamp
  @Column(updatable = false)
  private LocalDateTime createdAt;

  @UpdateTimestamp
  private LocalDateTime updatedAt;
}
