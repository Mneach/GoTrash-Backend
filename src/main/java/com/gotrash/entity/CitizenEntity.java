package com.gotrash.entity;


import jakarta.annotation.Nullable;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Version;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigInteger;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "citizens", schema = "gotrash")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CitizenEntity {

  @Id
  private UUID userId;

  @OneToOne(cascade = CascadeType.ALL)
  @MapsId
  @JoinColumn(name = "user_id")
  private UserEntity user;

  @NotNull
  private String name;

  @NotNull
  private String phoneNumber;

  @Nullable
  private Integer currentStreak;

  @Nullable
  private Integer longestStreak;

  @Nullable
  private LocalDate lastTrashDate;

  @Nullable
  private String imageUrl;

  @NotNull
  private BigInteger coin;

  @NotNull
  private BigInteger rating;

  @CreationTimestamp
  @Column(updatable = false)
  private LocalDateTime createdAt;

  @UpdateTimestamp
  private LocalDateTime updatedAt;

  @Version
  private Long version;
}
