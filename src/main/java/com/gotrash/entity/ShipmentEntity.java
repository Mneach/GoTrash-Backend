package com.gotrash.entity;

import com.gotrash.constant.ShipmentStatus;
import com.gotrash.constant.ShipmentTrashCategory;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "shipments")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ShipmentEntity {
  @Id
  @GeneratedValue
  private UUID shipmentId;

  @ManyToOne
  private WasteBankEntity wasteBank;

  @Enumerated(EnumType.STRING)
  private ShipmentTrashCategory category;

  private Double weight;

  @ManyToOne
  private CompanyEntity destinationCompany;

  private Double price;

  @Enumerated(EnumType.STRING)
  private ShipmentStatus status;

  @CreationTimestamp
  @Column(updatable = false)
  private LocalDateTime createdAt;

  @UpdateTimestamp
  private LocalDateTime updatedAt;
}
