package com.gotrash.api.v1.transformer;

import com.gotrash.api.v1.model.streak.Streak;
import com.gotrash.api.v1.response.streak.StreakResponse;
import com.gotrash.api.v1.response.streak.StreakTrashHistoryResponse;

public class StreakTransformer {

  public static StreakResponse transformModelToResponse(Streak streak) {
    return StreakResponse.builder()
        .startDate(streak.getStartDate())
        .endDate(streak.getEndDate())
        .totalStreak(streak.getTotalStreak())
        .trashHistories(
            streak.getTrashHistories()
                .stream()
                .map(TrashHistoryTransformer::transformModelToResponse)
                .toList()
        )
        .build();
  }
}
