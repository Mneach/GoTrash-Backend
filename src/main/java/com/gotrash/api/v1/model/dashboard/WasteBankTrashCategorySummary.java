package com.gotrash.api.v1.model.dashboard;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class WasteBankTrashCategorySummary {
  private UUID wasteBankId;
  private String wasteBankName;
  private String trashCategory;
  private Long totalTrash;
}
