package com.gotrash.service;

import com.gotrash.api.v1.model.Mission;
import com.gotrash.api.v1.transformer.MissionTransformer;
import com.gotrash.entity.MissionEntity;
import com.gotrash.entity.TrashCategoryEntity;
import com.gotrash.repository.MissionRepository;
import com.gotrash.repository.TrashCategoryRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MissionService {

  private final MissionRepository missionRepository;
  private final TrashCategoryRepository trashCategoryRepository;

  @Transactional
  public Mission save(Mission mission) {
    TrashCategoryEntity trashCategoryEntity = null;

    if (mission.getTrashCategory() != null && mission.getTrashCategory().getTrashCategoryId() != null) {
      Optional<TrashCategoryEntity> trashCategoryEntityOptional = trashCategoryRepository.findById(UUID.fromString(mission.getTrashCategory().getTrashCategoryId()));

      if (trashCategoryEntityOptional.isPresent()) {
        trashCategoryEntity = trashCategoryEntityOptional.get();
      }
    }

    MissionEntity missionEntity = MissionTransformer.transformModelToEntity(mission);
    missionEntity.setTrashCategory(trashCategoryEntity);

    return MissionTransformer.transformEntityToModel(
        missionRepository.save(missionEntity)
    );
  }

  @Transactional
  public List<Mission> getAllMission() {
    List<MissionEntity> missionEntities = missionRepository.findAll();

    return missionEntities
        .stream()
        .map(MissionTransformer::transformEntityToModel)
        .toList();
  }

  @Transactional
  public List<Mission> getAllMissionFilterByMissionGoalType(String goalType) {
    List<MissionEntity> missionEntities = missionRepository.findAllByGoalType(goalType);

    return missionEntities
        .stream()
        .map(MissionTransformer::transformEntityToModel)
        .toList();
  }

  @Transactional
  public List<Mission> getAllMissionFilterByMissionType(String type) {
    List<MissionEntity> missionEntities = missionRepository.findAllByType(type);

    return missionEntities
        .stream()
        .map(MissionTransformer::transformEntityToModel)
        .toList();
  }

  @Transactional
  public Mission getMissionById(String missionId) {
    MissionEntity missionEntity = missionRepository.findById(UUID.fromString(missionId))
        .orElseThrow(() -> new EntityNotFoundException("Mission with id " + missionId + " Not Found"));

    return MissionTransformer.transformEntityToModel(
        missionEntity
    );
  }

  public Mission update(Mission mission) {

    MissionEntity missionEntity = missionRepository.findById(UUID.fromString(mission.getMissionId()))
        .orElseThrow(() -> new EntityNotFoundException("Mission with id " + mission.getMissionId() + " Not Found"));

    missionEntity.setTitle(mission.getTitle() != null ? mission.getTitle() : missionEntity.getTitle());
    missionEntity.setRewardCoins(mission.getRewardCoins() != null ? mission.getRewardCoins() : missionEntity.getRewardCoins());
    missionEntity.setDescription(mission.getDescription() != null ? mission.getDescription() : missionEntity.getDescription());

    return MissionTransformer.transformEntityToModel(
        missionRepository.save(missionEntity)
    );

  }

  public void delete(String missionId) {
    MissionEntity missionEntity = missionRepository.findById(UUID.fromString(missionId))
        .orElseThrow(() -> new EntityNotFoundException("Mission with id " + missionId + " Not Found"));

    missionRepository.delete(missionEntity);
  }
}
