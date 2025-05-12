package com.gotrash.api.v1.response.streak;

import com.gotrash.api.v1.model.streak.StreakTrashHistory;
import com.gotrash.api.v1.response.TrashHistoryResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StreakResponse {
  private LocalDate startDate;
  private LocalDate endDate;
  private Integer totalStreak;
  private List<TrashHistoryResponse> trashHistories;
}
