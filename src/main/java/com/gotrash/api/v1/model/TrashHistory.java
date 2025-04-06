package com.gotrash.api.v1.model;

import com.gotrash.api.v1.response.TrashResponse;
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
public class TrashHistory {
    private String trashHistoryId;
    private User user;
    private Trash trash;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
