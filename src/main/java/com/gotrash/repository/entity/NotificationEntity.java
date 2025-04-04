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

import java.time.LocalDateTime;

@Entity
@Table(name = "notification", schema = "gotrash")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class NotificationEntity {

  @Id
  @Column(updatable = false, nullable = false)
  private String id;

  @NotNull
  @OneToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "user_id")
  private String userId;

  @NotNull
  private String title;

  @NotNull
  private String description;

  @CreationTimestamp
  @Column(updatable = false)
  private LocalDateTime createdAt;

  @UpdateTimestamp
  private LocalDateTime updatedAt;
}
