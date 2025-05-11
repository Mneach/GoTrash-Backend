package com.gotrash.entity;

import com.gotrash.entity.id.WasteBankWarehouseId;
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

@Entity
@Table(name = "waste_bank_warehouses", schema = "gotrash")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class WasteBankWarehouseEntity {

  @EmbeddedId
  private WasteBankWarehouseId wasteBankWarehouseId;

  @ManyToOne
  @MapsId("wasteBankId")
  @JoinColumn(name = "waste_bank_id")
  private WasteBankEntity wasteBankEntity;

  @ManyToOne
  @MapsId("trashCategoryId")
  @JoinColumn(name = "trash_category_id")
  private TrashCategoryEntity trashCategoryEntity;

  @NotNull
  @Column(precision = 19, scale = 2)
  private BigDecimal totalWeight;

  @CreationTimestamp
  @Column(updatable = false)
  private LocalDateTime createdAt;

  @UpdateTimestamp
  private LocalDateTime updatedAt;
}
