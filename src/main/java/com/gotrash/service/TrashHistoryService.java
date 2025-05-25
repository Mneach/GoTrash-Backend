package com.gotrash.service;

import com.gotrash.api.v1.model.*;
import com.gotrash.api.v1.model.trashhistory.TrashHistory;
import com.gotrash.api.v1.model.trashhistory.TrashHistoryManual;
import com.gotrash.api.v1.model.trashhistory.TrashHistoryWasteBank;
import com.gotrash.api.v1.transformer.trashhistory.TrashHistoryTransformer;
import com.gotrash.api.v1.transformer.trashhistory.TrashHistoryWasteBankTransformer;
import com.gotrash.entity.CitizenEntity;
import com.gotrash.entity.TrashBinEntity;
import com.gotrash.entity.TrashEntity;
import com.gotrash.entity.TrashHistoryEntity;
import com.gotrash.entity.id.WasteBankWarehouseId;
import com.gotrash.repository.CitizenRepository;
import com.gotrash.repository.TrashBinRepository;
import com.gotrash.repository.TrashHistoryRepository;
import com.gotrash.repository.TrashRepository;
import com.gotrash.util.CalculatorUtil;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TrashHistoryService {

    private final TrashHistoryRepository trashHistoryRepository;
    private final WasteBankWarehouseService wasteBankWarehouseService;
    private final CitizenService citizenService;
    private final StreakService streakService;
    private final CitizenRepository citizenRepository;
    private final TrashRepository trashRepository;
    private final TrashBinRepository trashBinRepository;

    @Transactional
    public TrashHistory save(TrashHistory trashHistory) {

        CitizenEntity citizenEntity = citizenRepository.findByUser_UserId(UUID.fromString(trashHistory.getCitizen().getUserId()))
            .orElseThrow(() -> new EntityNotFoundException("Citizen not found"));

        TrashEntity trashEntity = trashRepository.findById(UUID.fromString(trashHistory.getTrash().getTrashId()))
            .orElseThrow(() -> new EntityNotFoundException("Trash not found"));

        TrashBinEntity trashBinEntity = trashBinRepository.findById(UUID.fromString(trashHistory.getTrashBin().getTrashBinId()))
            .orElseThrow(() -> new EntityNotFoundException("TrashBin not found"));

        TrashHistoryEntity trashHistoryEntity = TrashHistoryTransformer.transformModelToEntity(trashHistory);
        trashHistoryEntity.setCitizen(citizenEntity);
        trashHistoryEntity.setTrash(trashEntity);
        trashHistoryEntity.setTrashBin(trashBinEntity);
        trashHistoryEntity = trashHistoryRepository.save(trashHistoryEntity);

        trashHistory = TrashHistoryTransformer.transformEntityToModel(trashHistoryEntity);

        BigInteger totalCoin = CalculatorUtil.calculateCoin(trashHistory.getWeight(), trashHistory.getTrash().getCoin());
        BigInteger totalRating = CalculatorUtil.calculateRating(trashHistory.getWeight(), trashHistory.getTrash().getRating());

        streakService.updateCitizenStreak(trashHistory.getCitizen());
        citizenService.addCoin(trashHistory.getCitizen().getUserId(), totalCoin);
        citizenService.addRating(trashHistory.getCitizen().getUserId(), totalRating);

        // Add the data into waste bank warehouse
        wasteBankWarehouseService.addTrashToWasteBankWarehouse(
            WasteBankWarehouse.builder()
                .wasteBankWarehouseId(
                    new WasteBankWarehouseId(
                        UUID.fromString(trashHistory.getTrashBin().getWasteBank().getUserId()),
                        UUID.fromString(trashHistory.getTrash().getTrashCategory().getTrashCategoryId())
                    )
                )
                .wasteBank(trashHistory.getTrashBin().getWasteBank())
                .trashCategory(trashHistory.getTrash().getTrashCategory())
                .totalWeight(trashHistory.getWeight())
                .build()
        );

        return TrashHistoryTransformer.transformEntityToModel(trashHistoryEntity, totalCoin);
    }


    public List<TrashHistory> getTrashHistories() {
        List<TrashHistoryEntity> trashHistoryEntities = trashHistoryRepository.findAll();

        return trashHistoryEntities.stream()
            .map(TrashHistoryTransformer::transformEntityToModel)
            .toList();
    }

    public TrashHistory storeTrashManually(TrashHistoryManual trashHistoryManual) {
        CitizenEntity citizenEntity = citizenRepository.findByPhoneNumber(trashHistoryManual.getPhoneNumber())
            .orElseThrow(() -> new EntityNotFoundException("Citizen not found"));

        TrashEntity trashEntity = trashRepository.findById(UUID.fromString(trashHistoryManual.getTrashId()))
            .orElseThrow(() -> new EntityNotFoundException("Trash not found"));

        TrashBinEntity trashBinEntity = trashBinRepository.findById(UUID.fromString(trashHistoryManual.getTrashBinId()))
            .orElseThrow(() -> new EntityNotFoundException("TrashBin not found"));

        TrashHistoryEntity trashHistoryEntity = TrashHistoryEntity.builder()
            .citizen(citizenEntity)
            .trash(trashEntity)
            .trashBin(trashBinEntity)
            .weight(trashHistoryManual.getWeight())
            .build();

        trashHistoryEntity = trashHistoryRepository.save(trashHistoryEntity);
        TrashHistory trashHistory = TrashHistoryTransformer.transformEntityToModel(trashHistoryEntity);

        BigInteger totalCoin = CalculatorUtil.calculateCoin(trashHistory.getWeight(), trashHistory.getTrash().getCoin());
        BigInteger totalRating = CalculatorUtil.calculateRating(trashHistory.getWeight(), trashHistory.getTrash().getRating());

        streakService.updateCitizenStreak(trashHistory.getCitizen());
        citizenService.addCoin(trashHistory.getCitizen().getUserId(), totalCoin);
        citizenService.addRating(trashHistory.getCitizen().getUserId(), totalRating);

        // Add the data into waste bank warehouse
        wasteBankWarehouseService.addTrashToWasteBankWarehouse(
            WasteBankWarehouse.builder()
                .wasteBankWarehouseId(
                    new WasteBankWarehouseId(
                        UUID.fromString(trashHistory.getTrashBin().getWasteBank().getUserId()),
                        UUID.fromString(trashHistory.getTrash().getTrashCategory().getTrashCategoryId())
                    )
                )
                .wasteBank(trashHistory.getTrashBin().getWasteBank())
                .trashCategory(trashHistory.getTrash().getTrashCategory())
                .totalWeight(trashHistory.getWeight())
                .build()
        );

        return TrashHistoryTransformer.transformEntityToModel(trashHistoryEntity, totalCoin);
    }

    public TrashHistory getTrashHistoryByTrashHistoryId(String trashHistoryId) {
        Optional<TrashHistoryEntity> trashHistoryEntityOptional = trashHistoryRepository.findById(UUID.fromString(trashHistoryId));

        if (trashHistoryEntityOptional.isEmpty()) {
            throw new EntityNotFoundException("Trash History with ID " + trashHistoryId + " Not Found");
        }

        return TrashHistoryTransformer.transformEntityToModel(trashHistoryEntityOptional.get());
    }

    public List<TrashHistory> getTrashHistoryByUserId(String userId) {
        List<TrashHistoryEntity> trashHistoryEntities = trashHistoryRepository.findAllByCitizen_UserId(UUID.fromString(userId));

        return trashHistoryEntities
                .stream()
                .map(TrashHistoryTransformer::transformEntityToModel)
                .toList();
    }

    @Transactional
    public TrashHistory update(TrashHistory trashHistory) {

        if (!trashHistoryRepository.existsById(UUID.fromString(trashHistory.getTrashHistoryId()))) {
            throw new EntityNotFoundException("Trash History with ID " + trashHistory.getTrashHistoryId() + " Not Found");
        }

        TrashHistoryEntity trashHistoryEntity = TrashHistoryTransformer.transformModelToEntity(trashHistory);

        if (trashHistory.getCitizen().getUserId() != null) {
            CitizenEntity citizenEntity = citizenRepository.findByUser_UserId(UUID.fromString(trashHistory.getCitizen().getUserId()))
                .orElseThrow(() -> new EntityNotFoundException("Citizen not found"));

            trashHistoryEntity.setCitizen(citizenEntity);
        }

        if (trashHistory.getTrash().getTrashId() != null) {
            TrashEntity trashEntity = trashRepository.findById(UUID.fromString(trashHistory.getTrash().getTrashId()))
                .orElseThrow(() -> new EntityNotFoundException("Trash not found"));

            trashHistoryEntity.setTrash(trashEntity);
        }

        if (trashHistory.getTrashBin().getTrashBinId() != null) {
            TrashBinEntity trashBinEntity = trashBinRepository.findById(UUID.fromString(trashHistory.getTrashBin().getTrashBinId()))
                .orElseThrow(() -> new EntityNotFoundException("TrashBin not found"));

            trashHistoryEntity.setTrashBin(trashBinEntity);
        }

        trashHistoryEntity.setWeight(trashHistory.getWeight() != null ? trashHistory.getWeight() : trashHistoryEntity.getWeight());
        trashHistoryEntity.setUpdatedAt(LocalDateTime.now());

        trashHistoryEntity = trashHistoryRepository.save(trashHistoryEntity);

        return TrashHistoryTransformer.transformEntityToModel(trashHistoryEntity);
    }

    @Transactional
    public void delete(String trashHistoryId) {
        if (!trashHistoryRepository.existsById(UUID.fromString(trashHistoryId))) {
            throw new EntityNotFoundException("Trash History with ID " + trashHistoryId + " Not Found");
        }

        trashHistoryRepository.deleteById(UUID.fromString(trashHistoryId));
    }

    @Transactional
    public List<TrashHistoryWasteBank> getTrashHistoriesByWasteBankId(String wasteBankId) {

        List<TrashHistoryEntity> trashHistoryEntities = trashHistoryRepository.findAllByWasteBankId(
            UUID.fromString(wasteBankId)
        );

        List<TrashHistory> trashHistories = trashHistoryEntities
            .stream()
            .map(TrashHistoryTransformer::transformEntityToModel)
            .toList();

        List<UUID> citizenIds = trashHistoryEntities.stream()
            .map(trashHistoryEntity -> trashHistoryEntity.getCitizen().getUserId())
            .toList();

        List<Citizen> citizens = citizenService.getCitizensByIds(citizenIds);

        Map<String, Citizen> citizenMap = citizens.stream()
            .collect(Collectors.toMap(
                Citizen::getUserId,
                Function.identity()
            ));


        return trashHistories
            .stream()
            .map(trashHistory -> TrashHistoryWasteBankTransformer.transformToModel(
                trashHistory,
                citizenMap.get(trashHistory.getCitizen().getUserId())
            ))
            .toList();
    }
}
