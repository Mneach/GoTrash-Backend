package com.gotrash.service;

import com.gotrash.api.v1.model.Citizen;
import com.gotrash.api.v1.model.trashhistory.TrashHistory;
import com.gotrash.api.v1.model.User;
import com.gotrash.api.v1.model.streak.Streak;
import com.gotrash.api.v1.transformer.CitizenTransformer;
import com.gotrash.api.v1.transformer.TrashHistoryTransformer;
import com.gotrash.entity.CitizenEntity;
import com.gotrash.entity.TrashHistoryEntity;
import com.gotrash.repository.CitizenRepository;
import com.gotrash.repository.TrashHistoryRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StreakService {

  private final CitizenRepository citizenRepository;
  private final TrashHistoryRepository trashHistoryRepository;

  @Transactional
  public void updateCitizenStreak(User user) {
    CitizenEntity citizenEntity = citizenRepository.findById(UUID.fromString(user.getUserId()))
        .orElseThrow(() -> new EntityNotFoundException("Citizen not found"));

    LocalDate today = LocalDate.now();
    LocalDate lastTrashDate = citizenEntity.getLastTrashDate();

    if (lastTrashDate != null) {
      if (lastTrashDate.equals(today)) {
        // Already submitted today -> No change
        return;
      } else if (lastTrashDate.equals(today.minusDays(1))) {
        // Continued streak
        citizenEntity.setCurrentStreak(citizenEntity.getCurrentStreak() + 1);
      } else {
        // Streak broken
        citizenEntity.setCurrentStreak(1);
        citizenEntity.setLongestStreak(1);
      }
    } else {
      // First time throwing trash
      citizenEntity.setCurrentStreak(1);
      citizenEntity.setLongestStreak(1);
    }

    citizenEntity.setLastTrashDate(today);

    // Update longest streak
    if (citizenEntity.getCurrentStreak() > citizenEntity.getLongestStreak()) {
      citizenEntity.setLongestStreak(citizenEntity.getCurrentStreak());
    }

    citizenRepository.save(citizenEntity);
  }

  public List<Streak> getDailyStreaks(String citizenId) {
    UUID citizenUUID = UUID.fromString(citizenId);

    CitizenEntity citizenEntity = citizenRepository.findById(citizenUUID)
        .orElseThrow(() -> new EntityNotFoundException("Citizen not found"));

    // Step 1: Get all trash history
    List<TrashHistoryEntity> allTrashHistory = trashHistoryRepository.findAllByUser_UserId(citizenUUID);

    if (allTrashHistory.isEmpty()) {
      return Collections.emptyList();
    }

    // Step 2: Group by date
    Map<LocalDate, List<TrashHistoryEntity>> trashGroupedByDate = allTrashHistory.stream()
        .collect(Collectors.groupingBy(e -> e.getCreatedAt().toLocalDate()));

    // Step 3: Get range from first date to last date in history
    LocalDate firstDate = allTrashHistory.stream()
        .map(e -> e.getCreatedAt().toLocalDate())
        .min(LocalDate::compareTo)
        .orElseThrow();

    LocalDate lastDate = allTrashHistory.stream()
        .map(e -> e.getCreatedAt().toLocalDate())
        .max(LocalDate::compareTo)
        .orElseThrow();

    // Step 4: Build streaks per day, backwards
    List<Streak> streaks = new ArrayList<>();
    boolean brokenStreak = false;
    LocalDate currentDate = lastDate;

    while (!currentDate.isBefore(firstDate)) {
      List<TrashHistoryEntity> trashOnDate = trashGroupedByDate.getOrDefault(currentDate, Collections.emptyList());

      int totalStreak = 0;
      List<TrashHistory> transformed = trashOnDate.stream()
          .map(TrashHistoryTransformer::transformEntityToModel)
          .toList();

      if (!brokenStreak) {
        if (!trashOnDate.isEmpty()) {
          totalStreak = 1;
        } else {
          brokenStreak = true;
        }
      }

      // Prepend to keep chronological order
      streaks.add(0, Streak.builder()
          .startDate(currentDate)
          .endDate(currentDate)
          .totalStreak(totalStreak)
          .trashHistories(transformed)
          .build());

      currentDate = currentDate.minusDays(1);
    }

    return streaks;
  }



}
