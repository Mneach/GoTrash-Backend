package com.gotrash.api.v1.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TrashCategoryResponse {
  private String trashCategoryId;
  private String name;
  private LocalDateTime createdAt;
  private LocalDateTime updatedAt;
}
