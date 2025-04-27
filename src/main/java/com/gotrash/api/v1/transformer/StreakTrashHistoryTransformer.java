package com.gotrash.api.v1.transformer;

import com.gotrash.api.v1.model.streak.StreakTrashHistory;
import com.gotrash.api.v1.response.streak.StreakTrashHistoryResponse;

public class StreakTrashHistoryTransformer {

  public static StreakTrashHistoryResponse transformModelToResponse(StreakTrashHistory streakTrashHistory) {
    return StreakTrashHistoryResponse.builder()
        .name(streakTrashHistory.getName())
        .category(streakTrashHistory.getCategory())
        .weight(streakTrashHistory.getWeight())
        .totalCoin(streakTrashHistory.getTotalCoin())
        .build();
  }
}
