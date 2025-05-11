package com.gotrash.entity.id;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.io.Serializable;
import java.util.UUID;

@Embeddable
public class WasteBankWarehouseId implements Serializable {

  @Column(name = "waste_bank_id")
  private UUID wasteBankId;

  @Column(name = "trash_category_id")
  private UUID trashCategoryId;

  // Constructors
  public WasteBankWarehouseId() {}

  public WasteBankWarehouseId(UUID wasteBankId, UUID trashCategoryId) {
    this.wasteBankId = wasteBankId;
    this.trashCategoryId = trashCategoryId;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    WasteBankWarehouseId that = (WasteBankWarehouseId) o;

    if (!wasteBankId.equals(that.wasteBankId)) return false;
    return trashCategoryId.equals(that.trashCategoryId);
  }

  @Override
  public int hashCode() {
    int result = wasteBankId.hashCode();
    result = 31 * result + trashCategoryId.hashCode();
    return result;
  }
}