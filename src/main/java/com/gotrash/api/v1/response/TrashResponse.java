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
  private String name;
  private TrashCategoryResponse trashCategory;
  private BigInteger coin;
  private BigInteger rating;
  private LocalDateTime createdAt;
  private LocalDateTime updatedAt;
}
