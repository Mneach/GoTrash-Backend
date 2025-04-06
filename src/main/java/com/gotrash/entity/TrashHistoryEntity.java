package com.gotrash.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "trash_histories", schema = "gotrash")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TrashHistoryEntity {
  @Id
  @Column(updatable = false, nullable = false, columnDefinition = "UUID")
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID trashHistoryId;

  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "user_id", nullable = false)
  private UserEntity user;

  @OneToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "trash_id", nullable = false)
  private TrashEntity trash;

  @CreationTimestamp
  @Column(updatable = false)
  private LocalDateTime createdAt;

  @UpdateTimestamp
  private LocalDateTime updatedAt;
}
