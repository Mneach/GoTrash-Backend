package com.gotrash.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@Table(
    name = "group_members",
    schema = "gotrash",
    uniqueConstraints = @UniqueConstraint(
        columnNames = {"group_id", "user_id"}
    )
)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GroupMemberEntity {

  @Id
  @Column(updatable = false, nullable = false, columnDefinition = "UUID")
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID groupMemberId;

  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "citizen_id", nullable = false)
  private CitizenEntity citizen;

  @ManyToOne
  @JoinColumn(name = "group_id", nullable = false)
  @JsonIgnore
  private GroupEntity group;

  @CreationTimestamp
  @Column(updatable = false)
  private LocalDateTime createdAt;

  @UpdateTimestamp
  private LocalDateTime updatedAt;
}
