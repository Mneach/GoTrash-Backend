package com.gotrash.entity;

import com.gotrash.constant.RoleName;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
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
@Table(name = "roles", schema = "gotrash")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RoleEntity {

  @Id
  @Column(updatable = false, nullable = false, columnDefinition = "UUID")
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID roleId;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false, unique = true)
  private RoleName name;

  @CreationTimestamp
  @Column(updatable = false)
  private LocalDateTime createdAt;

  @UpdateTimestamp
  private LocalDateTime updatedAt;

}
