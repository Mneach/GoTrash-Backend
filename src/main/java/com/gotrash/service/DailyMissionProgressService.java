package com.gotrash.service;

import com.gotrash.api.v1.model.DailyMissionProgress;
import com.gotrash.api.v1.transformer.DailyMissionProgressTransformer;
import com.gotrash.api.v1.model.Mission;
import com.gotrash.api.v1.transformer.MissionTransformer;
import com.gotrash.api.v1.model.Citizen;
import com.gotrash.api.v1.transformer.CitizenTransformer;
import com.gotrash.entity.CitizenEntity;
import com.gotrash.entity.DailyMissionProgressEntity;
import com.gotrash.entity.MissionEntity;
import com.gotrash.exception.rest.BadRequestException;
import com.gotrash.exception.rest.EntityNotFoundException;
import com.gotrash.repository.CitizenRepository;
import com.gotrash.repository.DailyMissionProgressRepository;
import com.gotrash.repository.MissionRepository;
import com.gotrash.util.DateUtil;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Random;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DailyMissionProgressService {

  private final DailyMissionProgressRepository dailyMissionProgressRepository;
  private final CitizenRepository citizenRepository;
  private final MissionRepository missionRepository;

  @Transactional
  public DailyMissionProgress save(DailyMissionProgress dailyMissionProgress) {

    CitizenEntity citizenEntity = citizenRepository.findById(UUID.fromString(dailyMissionProgress.getCitizen().getUserId()))
        .orElseThrow(() -> new jakarta.persistence.EntityNotFoundException("Citizen with ID " + dailyMissionProgress.getCitizen().getUserId() + " not found"));

    MissionEntity missionEntity = missionRepository.findById(UUID.fromString(dailyMissionProgress.getMission().getMissionId()))
        .orElseThrow(() -> new jakarta.persistence.EntityNotFoundException("Mission with id " + dailyMissionProgress.getMission().getMissionId() + " Not Found"));

    DailyMissionProgressEntity dailyMissionProgressEntity = DailyMissionProgressTransformer.transformModelToEntity(dailyMissionProgress);
    dailyMissionProgressEntity.setMission(missionEntity);
    dailyMissionProgressEntity.setCitizen(citizenEntity);
    dailyMissionProgressEntity.setActiveDate(DateUtil.getCurrentDate());

    return DailyMissionProgressTransformer.transformEntityToModel(
        dailyMissionProgressRepository.save(dailyMissionProgressEntity)
    );
  }

  @Transactional
  public void updateCitizenDailyMissionProgress(String citizenId, String trashCategoryId, BigDecimal trashWeight) {
    LocalDate today = DateUtil.getCurrentDate();

    List<DailyMissionProgressEntity> dailyMissionProgressEntities = dailyMissionProgressRepository.findByCitizenIdAndActiveDate(
        UUID.fromString(citizenId), today
    );

    dailyMissionProgressEntities.forEach(
        dailyMissionProgressEntity -> {
          MissionEntity missionEntity = dailyMissionProgressEntity.getMission();

          if (missionEntity.getTrashCategory() != null && missionEntity.getTrashCategory().getTrashCategoryId() != null) {
            // Update based on the trash category
            if (missionEntity.getTrashCategory().getTrashCategoryId().toString().equals(trashCategoryId)) {

              if (missionEntity.getGoalType().toLowerCase().contains("weight")) {
                handleWeightMission(dailyMissionProgressEntity, trashWeight);
              } else {
                handleNonWeightMission(dailyMissionProgressEntity);
              }

            }
          } else if (missionEntity.getGoalType().toLowerCase().contains("weight")) {
            handleWeightMission(dailyMissionProgressEntity, trashWeight);
          }else {
            handleNonWeightMission(dailyMissionProgressEntity);
          }

          if (dailyMissionProgressEntity.getCurrentProgress().compareTo(dailyMissionProgressEntity.getMission().getTargetValue()) > 0) {
            // Citizen has been reached the target
            dailyMissionProgressEntity.setCurrentProgress(dailyMissionProgressEntity.getMission().getTargetValue());
          }

          dailyMissionProgressRepository.save(dailyMissionProgressEntity);
        }
    );
  }

  @Transactional
  public List<DailyMissionProgress> getAllDailyMissionProgress() {
    List<DailyMissionProgressEntity> dailyMissionProgressEntities = dailyMissionProgressRepository.findAll();

    return dailyMissionProgressEntities
        .stream()
        .map(DailyMissionProgressTransformer::transformEntityToModel)
        .toList();
  }

  @Transactional
  public List<DailyMissionProgress> getAllDailyMissionProgressByCitizenId(String citizenId) {
    List<DailyMissionProgressEntity> dailyMissionProgressEntities = dailyMissionProgressRepository.findAllByCitizen_UserId(
        UUID.fromString(citizenId)
    );

    return dailyMissionProgressEntities
        .stream()
        .map(DailyMissionProgressTransformer::transformEntityToModel)
        .toList();
  }

  @Transactional
  public List<DailyMissionProgress> getAllActiveDailyMissionProgressByCitizenId(String citizenId) {
    LocalDate today = DateUtil.getCurrentDate();

    List<DailyMissionProgressEntity> dailyMissionProgressEntities = dailyMissionProgressRepository.findByCitizenIdAndActiveDate(
        UUID.fromString(citizenId), today
    );

    return dailyMissionProgressEntities
        .stream()
        .map(DailyMissionProgressTransformer::transformEntityToModel)
        .toList();
  }

  @Transactional
  public DailyMissionProgress claimDailyMissionReward(String dailyMissionProgressId) {
    DailyMissionProgressEntity dailyMissionProgressEntity = dailyMissionProgressRepository.findById(
        UUID.fromString(dailyMissionProgressId)
    ).orElseThrow(() -> new EntityNotFoundException(" Daily Mission Progress With ID " + dailyMissionProgressId + " Not Found"));

    // validate if user can claim the reward
    if (dailyMissionProgressEntity.getCurrentProgress().compareTo(dailyMissionProgressEntity.getMission().getTargetValue()) < 0) {
      // Progress hasn't reached target
      throw new BadRequestException("Current Progress hasn't reached target");
    }

    // validate if user already claimed the reward
    if (dailyMissionProgressEntity.getIsRewardClaimed()) {
      throw new BadRequestException("Your already claimed the reward");
    }

    // Update Citizen Coin
    dailyMissionProgressEntity.getCitizen().setCoin(
        dailyMissionProgressEntity.getCitizen().getCoin().add(dailyMissionProgressEntity.getMission().getRewardCoins())
    );

    // Update Citizen Rating
    dailyMissionProgressEntity.getCitizen().setRating(
        dailyMissionProgressEntity.getCitizen().getRating().add(
            dailyMissionProgressEntity.getMission().getRewardRatings()
        )
    );

    // update is reward claimed to true
    dailyMissionProgressEntity.setIsRewardClaimed(true);

    return DailyMissionProgressTransformer.transformEntityToModel(
        dailyMissionProgressRepository.save(dailyMissionProgressEntity)
    );
  }

  @Transactional
  public void assignDailyMissionsToAllCitizens() {
    LocalDate today = DateUtil.getCurrentDate();

    // Get all active missions once
    List<Mission> activeMissions = missionRepository.findAllByType("DAILY")
        .stream()
        .map(MissionTransformer::transformEntityToModel)
        .toList();

    if (activeMissions.isEmpty()) {
      return;
    }

    // Get all citizens who don't have a mission today
    List<Citizen> citizensNeedingMissions = getCitizensWithoutMissions(today);

    // Assign random missions to each citizen
    assignRandomMissions(citizensNeedingMissions, activeMissions, today);
  }

  @Transactional
  private List<Citizen> getCitizensWithoutMissions(LocalDate today) {
    return dailyMissionProgressRepository.findCitizensWithoutMissionsToday(today)
        .stream()
        .map(CitizenTransformer::transformEntityToModel)
        .toList();
  }

  @Transactional
  public void assignDailyMissionToOneCitizen(Citizen citizen) {
    LocalDate today = DateUtil.getCurrentDate();

    // Get all active missions once
    List<Mission> activeMissions = missionRepository.findAll()
        .stream()
        .map(MissionTransformer::transformEntityToModel)
        .toList();

    if (activeMissions.isEmpty()) {
      return;
    }

    assignRandomMissions(List.of(citizen), activeMissions, today);
  }

  @Transactional
  private void assignRandomMissions(List<Citizen> citizens, List<Mission> missions, LocalDate date) {
    Random random = new Random();

    citizens.forEach(citizen -> {
      Mission randomMission = missions.get(random.nextInt(missions.size()));

      DailyMissionProgress dailyMissionProgress = DailyMissionProgress.builder()
          .mission(randomMission)
          .citizen(citizen)
          .activeDate(date)
          .currentProgress(BigDecimal.ZERO)
          .isRewardClaimed(false)
          .build();

      this.save(dailyMissionProgress);
    });


  }

  private void handleNonWeightMission(DailyMissionProgressEntity dailyMissionProgressEntity) {
    dailyMissionProgressEntity.setCurrentProgress(
        dailyMissionProgressEntity.getCurrentProgress().add(BigDecimal.valueOf(1))
    );
  }

  private void handleWeightMission(DailyMissionProgressEntity dailyMissionProgressEntity, BigDecimal trashWeight) {
    dailyMissionProgressEntity.setCurrentProgress(
        dailyMissionProgressEntity.getCurrentProgress().add(trashWeight)
    );
  }
}
