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
public class WasteBankTrashSummary {
  private UUID wasteBankId;
  private String wasteBankName;
  private Long totalTrash;
}
