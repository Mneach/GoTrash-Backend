package com.gotrash.api.v1.model;

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
public class Trash {
    private String trashId;
    private TrashCategory trashCategory;
    private BigInteger coin;
    private String description;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
