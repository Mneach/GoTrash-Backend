package com.gotrash.entity;

import com.gotrash.api.v1.model.Citizen;
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
@Table(name = "citizen_addresses", schema = "gotrash")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CitizenAddressEntity {

  @Id
  @Column(updatable = false, nullable = false, columnDefinition = "UUID")
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID citizenAddressId;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "citizen_id")
  private CitizenEntity citizen;

  @Column(nullable = false)
  private String label;

  @Column(nullable = false)
  private String address;

  @Column(nullable = false)
  private String note;

  @CreationTimestamp
  @Column(name = "created_at", updatable = false)
  private LocalDateTime createdAt;

  @UpdateTimestamp
  @Column(name = "updated_at")
  private LocalDateTime updatedAt;
}
