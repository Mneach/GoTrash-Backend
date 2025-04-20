package com.gotrash.api.v1.response;

import com.gotrash.api.v1.model.Trash;
import com.gotrash.api.v1.model.User;
import com.gotrash.entity.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TrashHistoryResponse {
  private String trashHistoryId;
  private UserResponse user;
  private TrashResponse trash;
  private LocalDateTime createdAt;
  private LocalDateTime updatedAt;
}
