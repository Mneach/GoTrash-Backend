package com.gotrash.service;

import com.gotrash.api.v1.model.*;
import com.gotrash.api.v1.model.trashhistory.TrashHistory;
import com.gotrash.api.v1.model.trashhistory.TrashHistoryIoT;
import com.gotrash.api.v1.model.trashhistory.TrashHistoryManual;
import com.gotrash.api.v1.model.trashhistory.TrashHistoryWasteBank;
import com.gotrash.api.v1.transformer.TrashBinTransformer;
import com.gotrash.api.v1.transformer.trashhistory.TrashHistoryTransformer;
import com.gotrash.api.v1.transformer.TrashTransformer;
import com.gotrash.api.v1.transformer.trashhistory.TrashHistoryWasteBankTransformer;
import com.gotrash.entity.TrashHistoryEntity;
import com.gotrash.entity.id.WasteBankWarehouseId;
import com.gotrash.repository.TrashHistoryRepository;
import com.gotrash.util.CalculatorUtil;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TrashHistoryService {

    private final TrashHistoryRepository trashHistoryRepository;
    private final WasteBankWarehouseService wasteBankWarehouseService;
    private final UserService userService;
    private final TrashService trashService;
    private final TrashBinService trashBinService;
    private final StreakService streakService;
    private final CitizenService citizenService;

    @Transactional
    public TrashHistory save(TrashHistory trashHistory) {
        User user = userService.getUserByUserId(trashHistory.getCitizen().getUserId());
        Trash trash = trashService.getTrashByTrashId(trashHistory.getTrash().getTrashId());
        TrashBin trashBin = trashBinService.getTrashBinByTrashBinId(trashHistory.getTrashBin().getTrashBinId());

        trashHistory.setCitizen(user);
        trashHistory.setTrash(trash);
        trashHistory.setTrashBin(trashBin);
        trashHistory.setBleId(BigInteger.valueOf(trashHistoryRepository.getTotalUser() + 1));

        TrashHistoryEntity trashHistoryEntity = trashHistoryRepository.save(
                TrashHistoryTransformer.transformModelToEntity(trashHistory)
        );

        BigInteger totalCoin = CalculatorUtil.calculateCoin(trashHistory.getWeight(), trash.getCoin());
        BigInteger totalRating = CalculatorUtil.calculateRating(trashHistory.getWeight(), trashHistory.getTrash().getRating());

        streakService.updateCitizenStreak(user);
        citizenService.addCoin(user.getUserId(), totalCoin);
        citizenService.addRating(user.getUserId(), totalRating);

        // Add the data into waste bank warehouse
        wasteBankWarehouseService.addTrashToWasteBankWarehouse(
            WasteBankWarehouse.builder()
                .wasteBankWarehouseId(
                    new WasteBankWarehouseId(
                        UUID.fromString(trashBin.getWasteBank().getUserId()),
                        UUID.fromString(trash.getTrashCategory().getTrashCategoryId())
                    )
                )
                .wasteBank(trashBin.getWasteBank())
                .trashCategory(trash.getTrashCategory())
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
        Citizen citizen = citizenService.findCitizenByPhoneNumber(trashHistoryManual.getPhoneNumber());
        Trash trash = trashService.getTrashByTrashId(trashHistoryManual.getTrashId());
        TrashBin trashBin = trashBinService.getTrashBinByTrashBinId(trashHistoryManual.getTrashBinId());

        TrashHistory trashHistory = TrashHistory.builder()
            .citizen(citizen.getUser())
            .trash(trash)
            .trashBin(trashBin)
            .bleId(BigInteger.valueOf(trashHistoryRepository.getTotalUser() + 1))
            .weight(trashHistoryManual.getWeight())
            .build();

        TrashHistoryEntity trashHistoryEntity = trashHistoryRepository.save(
            TrashHistoryTransformer.transformModelToEntity(trashHistory)
        );

        BigInteger totalCoin = CalculatorUtil.calculateCoin(trashHistory.getWeight(), trash.getCoin());
        BigInteger totalRating = CalculatorUtil.calculateRating(trashHistory.getWeight(), trashHistory.getTrash().getRating());

        streakService.updateCitizenStreak(citizen.getUser());
        citizenService.addCoin(citizen.getUser().getUserId(), totalCoin);
        citizenService.addRating(citizen.getUser().getUserId(), totalRating);

        return TrashHistoryTransformer.transformEntityToModel(trashHistoryEntity, totalCoin);
    }

    @Transactional
    public TrashHistory storeTrashFromIoT(TrashHistoryIoT trashHistoryIoT) {
        Citizen citizen = citizenService.findCitizenByBleId(trashHistoryIoT.getBleId());
        Trash trash = trashService.getTrashByTrashName(trashHistoryIoT.getTrashName());
        TrashBin trashBin = trashBinService.getTrashBinByTrashBinId(trashHistoryIoT.getTrashBinId());

        TrashHistory trashHistory = TrashHistory.builder()
            .citizen(citizen.getUser())
            .trash(trash)
            .trashBin(trashBin)
            .bleId(BigInteger.valueOf(trashHistoryRepository.getTotalUser() + 1))
            .weight(trashHistoryIoT.getWeight())
            .build();

        TrashHistoryEntity trashHistoryEntity = trashHistoryRepository.save(
            TrashHistoryTransformer.transformModelToEntity(trashHistory)
        );

        BigInteger totalCoin = CalculatorUtil.calculateCoin(trashHistoryIoT.getWeight(), trash.getCoin());
        BigInteger totalRating = CalculatorUtil.calculateRating(trashHistoryIoT.getWeight(), trash.getRating());

        streakService.updateCitizenStreak(citizen.getUser());
        citizenService.addCoin(citizen.getUser().getUserId(), totalCoin);
        citizenService.addRating(citizen.getUser().getUserId(), totalRating);

        return TrashHistoryTransformer.transformEntityToModel(trashHistoryEntity, totalCoin);
    }

    public TrashHistory getTrashHistoryByTrashHistoryId(String trashHistoryId) {
        Optional<TrashHistoryEntity> trashHistoryEntityOptional = trashHistoryRepository.findById(UUID.fromString(trashHistoryId));

        if (trashHistoryEntityOptional.isEmpty()) {
            throw new EntityNotFoundException("Trash History with ID " + trashHistoryId + " Not Found");
        }

        return TrashHistoryTransformer.transformEntityToModel(trashHistoryEntityOptional.get());
    }

    public TrashHistory getTrashHistoryByTrashHistoryBleId(BigInteger bleId) {
        Optional<TrashHistoryEntity> trashHistoryEntityOptional = trashHistoryRepository.findByBleId(bleId);

        if (trashHistoryEntityOptional.isEmpty()) {
            throw new EntityNotFoundException("Trash History with Ble ID " + bleId + " Not Found");
        }

        return TrashHistoryTransformer.transformEntityToModel(trashHistoryEntityOptional.get());
    }

    public List<TrashHistory> getTrashHistoryByUserId(String userId) {
        List<TrashHistoryEntity> trashHistoryEntities = trashHistoryRepository.findAllByUser_UserId(UUID.fromString(userId));

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

        User user = userService.getUserByUserId(trashHistory.getCitizen().getUserId());
        Trash trash = trashService.getTrashByTrashId(trashHistory.getTrash().getTrashId());
        TrashBin trashBin = trashBinService.getTrashBinByTrashBinId(trashHistory.getTrashBin().getTrashBinId());

        trashHistory.setCitizen(user);
        trashHistory.setTrash(trash);
        trashHistory.setTrashBin(trashBin);
        trashHistory.setBleId(BigInteger.valueOf(trashHistoryRepository.getTotalUser() + 1));

        TrashHistoryEntity trashHistoryEntity = trashHistoryRepository.save(
                TrashHistoryTransformer.transformModelToEntity(trashHistory)
        );

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
            .map(trashHistoryEntity -> trashHistoryEntity.getUser().getUserId())
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
