package com.gotrash.api.v1.response;

import com.gotrash.api.v1.model.Reward;
import com.gotrash.api.v1.model.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ExchangeResponse {
  private String exchangeId;
  private User user;
  private Reward reward;
  private String status;
  private String description;
  private LocalDateTime createdAt;
  private LocalDateTime updatedAt;
}
