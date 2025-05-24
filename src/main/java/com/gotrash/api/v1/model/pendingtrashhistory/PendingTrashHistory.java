package com.gotrash.api.v1.model.pendingtrashhistory;

import com.gotrash.api.v1.model.Trash;
import com.gotrash.api.v1.model.TrashBin;
import com.gotrash.constant.PendingTrashHistoryStatus;
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
public class PendingTrashHistory {
    private String pendingTrashHistoryId;
    private Trash trash;
    private TrashBin trashBin;
    private BigDecimal weight;
    private BigInteger totalCoin;
    private PendingTrashHistoryStatus status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
