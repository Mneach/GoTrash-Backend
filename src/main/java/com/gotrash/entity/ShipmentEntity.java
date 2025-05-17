package com.gotrash.entity;

import com.gotrash.api.v1.model.TrashCategory;
import com.gotrash.constant.ShipmentStatus;
import com.gotrash.constant.ShipmentTrashCategory;
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
@Table(name = "shipments", schema = "gotrash")
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

  @NotNull
  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "trash_category_id", nullable = false)
  private TrashCategoryEntity trashCategory;

  @NotNull
  @Column(precision = 19, scale = 2)
  private BigDecimal weight;

  @ManyToOne
  private CompanyEntity destinationCompany;

  @NotNull
  @Column(precision = 20, scale = 6)
  private BigDecimal price;

  private String status;

  @CreationTimestamp
  @Column(updatable = false)
  private LocalDateTime createdAt;

  @UpdateTimestamp
  private LocalDateTime updatedAt;
}
