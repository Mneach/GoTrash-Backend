package com.gotrash.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(
    name = "group_member_mission_contributions",
    schema = "gotrash",
    uniqueConstraints = {
        @UniqueConstraint(columnNames = {"group_mission_progress_id", "citizen_id"})
    }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GroupMemberMissionContributionEntity {
  @Id
  @Column(updatable = false, nullable = false, columnDefinition = "UUID")
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID contributionId;

  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "group_mission_progress_id", nullable = false)
  @JsonIgnore
  private GroupMissionProgressEntity groupMissionProgress;

  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "citizen_id", nullable = false)
  private CitizenEntity citizen;

  @Column(nullable = false)
  private BigDecimal contribution;

  @CreationTimestamp
  @Column(updatable = false)
  private LocalDateTime createdAt;

  @UpdateTimestamp
  private LocalDateTime updatedAt;
}
