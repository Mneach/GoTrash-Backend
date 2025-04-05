package com.gotrash.entity;

import jakarta.annotation.Nullable;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
@Table(name = "users", schema = "gotrash")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserEntity {

  @Id
  @Column(updatable = false, nullable = false, columnDefinition = "UUID")
  @GeneratedValue(strategy = GenerationType.UUID)
  private String userId;

  @NotNull
  private String username;

  @NotNull
  private String password;

  @NotNull
  private String email;

  @NotNull
  private String phoneNumber;

  @Nullable
  private String imageUrl;

  @NotNull
  private BigInteger coin;

  @CreationTimestamp
  @Column(updatable = false)
  private LocalDateTime createdAt;

  @UpdateTimestamp
  private LocalDateTime updatedAt;
}
