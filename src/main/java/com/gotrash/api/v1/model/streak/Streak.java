package com.gotrash.api.v1.model.streak;

import com.gotrash.api.v1.model.TrashHistory;
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
public class Streak {
  private LocalDate startDate;
  private LocalDate endDate;
  private Integer totalStreak;
  private List<StreakTrashHistory> trashHistory;
}
