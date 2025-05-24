package com.gotrash.entity;

import com.gotrash.constant.PendingTrashHistoryStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "pending_trash_histories", schema = "gotrash")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PendingTrashHistoryEntity {
  @Id
  @Column(updatable = false, nullable = false, columnDefinition = "UUID")
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID pendingTrashHistoryId;

  @OneToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "trash_id", nullable = false)
  private TrashEntity trash;

  @OneToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "trash_bin_id", nullable = false)
  private TrashBinEntity trashBin;

  @NotNull
  @Column(precision = 19, scale = 2)
  private BigDecimal weight;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false)
  private PendingTrashHistoryStatus status;

  @CreationTimestamp
  @Column(updatable = false)
  private LocalDateTime createdAt;

  @UpdateTimestamp
  private LocalDateTime updatedAt;
}
