package com.gotrash.api.v1.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Exchange {
  private String exchangeId;
  private User user;
  private Reward reward;
  private String status;
  private String description;
  private LocalDateTime createdAt;
  private LocalDateTime updatedAt;
}
