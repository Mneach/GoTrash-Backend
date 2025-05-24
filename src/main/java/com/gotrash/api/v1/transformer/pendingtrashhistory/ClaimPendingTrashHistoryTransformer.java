package com.gotrash.api.v1.transformer.pendingtrashhistory;

import com.gotrash.api.v1.model.pendingtrashhistory.ClaimPendingTrashHistory;
import com.gotrash.api.v1.response.pendingtrashhistory.ClaimPendingTrashHistoryResponse;

public class ClaimPendingTrashHistoryTransformer {

  public static ClaimPendingTrashHistoryResponse transformModelToResponse(ClaimPendingTrashHistory claimPendingTrashHistory) {
    return ClaimPendingTrashHistoryResponse.builder()
        .totalWeight(claimPendingTrashHistory.getTotalWeight())
        .totalRating(claimPendingTrashHistory.getTotalRating())
        .totalCoin(claimPendingTrashHistory.getTotalCoin())
        .build();
  }
}
