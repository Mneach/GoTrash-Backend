package com.gotrash.service;

import com.gotrash.api.v1.model.Citizen;
import com.gotrash.api.v1.model.TrashHistory;
import com.gotrash.api.v1.model.User;
import com.gotrash.api.v1.model.streak.Streak;
import com.gotrash.api.v1.model.streak.StreakTrashHistory;
import com.gotrash.api.v1.transformer.CitizenTransformer;
import com.gotrash.api.v1.transformer.TrashHistoryTransformer;
import com.gotrash.entity.CitizenEntity;
import com.gotrash.entity.TrashHistoryEntity;
import com.gotrash.repository.CitizenRepository;
import com.gotrash.repository.TrashHistoryRepository;
import com.gotrash.util.CalculatorUtil;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

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

  public Streak getStreak(String citizenId) {
    CitizenEntity citizenEntity = citizenRepository.findById(UUID.fromString(citizenId))
        .orElseThrow(() -> new EntityNotFoundException("Citizen not found"));

    Citizen citizen = CitizenTransformer.transformEntityToModel(citizenEntity);

    // Calculate startDate based on lastTrashDate and currentStreak
    LocalDate endDate = citizenEntity.getLastTrashDate();
    if (endDate == null) {
      throw new IllegalStateException("Citizen has no trash activity yet");
    }

    LocalDate startDate = endDate.minusDays(citizen.getCurrentStreak() - 1);

    LocalDateTime startDateTime = startDate.atStartOfDay();
    LocalDateTime endDateTime = endDate.atTime(23, 59, 59);


    // Get all trash in that range
    List<TrashHistoryEntity> trashHistoryEntities = trashHistoryRepository.findAllByUser_UserIdAndCreatedAtBetween(
        UUID.fromString(citizenId), startDateTime, endDateTime
    );

    List<TrashHistory> trashHistories = trashHistoryEntities.stream()
        .map(TrashHistoryTransformer::transformEntityToModel)
        .toList();

    List<StreakTrashHistory> streakTrashHistories = new ArrayList<>();

    for (TrashHistory trashHistory : trashHistories) {
      String trashName = trashHistory.getTrash().getName();
      String trashCategory = trashHistory.getTrash().getTrashCategory().getName();
      BigDecimal weight = trashHistory.getWeight();
      BigInteger baseCoin = trashHistory.getTrash().getCoin();
      BigInteger totalCoin = CalculatorUtil.calculateCoin(weight, baseCoin);

      StreakTrashHistory streakTrashHistory = StreakTrashHistory.builder()
          .name(trashName)
          .category(trashCategory)
          .weight(weight)
          .totalCoin(totalCoin)
          .build();

      streakTrashHistories.add(streakTrashHistory);
    }

    return Streak.builder()
        .startDate(startDate)
        .endDate(endDate)
        .totalStreak(citizen.getCurrentStreak())
        .trashHistory(streakTrashHistories)
        .build();
  }
}
