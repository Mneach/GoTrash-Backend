package com.gotrash.repository.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "trash_bin", schema = "gotrash")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TrashBinEntity {
  @Id
  @Column(updatable = false, nullable = false)
  private String id;

  @NotNull
  private Double latitude;

  @NotNull
  private Double longitude;

  @NotNull
  private String address;

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
