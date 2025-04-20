package com.gotrash.api.v1.response;

import com.gotrash.api.v1.model.TrashCategory;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigInteger;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TrashResponse {
  private String trashId;
  private TrashCategoryResponse trashCategory;
  private BigInteger coin;
  private String description;
  private LocalDateTime createdAt;
  private LocalDateTime updatedAt;
}
