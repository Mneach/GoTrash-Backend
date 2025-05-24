package com.gotrash.api.v1.model.trashhistory;

import com.gotrash.api.v1.model.Citizen;
import com.gotrash.api.v1.model.Trash;
import com.gotrash.api.v1.model.TrashBin;
import com.gotrash.api.v1.model.User;
import com.gotrash.api.v1.response.TrashResponse;
import com.gotrash.entity.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TrashHistory {
    private String trashHistoryId;
    private Citizen citizen;
    private Trash trash;
    private TrashBin trashBin;
    private BigDecimal weight;
    private BigInteger totalCoin;
    private BigInteger bleId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
