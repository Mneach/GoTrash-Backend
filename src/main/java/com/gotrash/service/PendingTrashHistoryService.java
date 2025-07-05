package com.gotrash.service;


import com.gotrash.api.v1.model.Notification;
import com.gotrash.api.v1.model.User;
import com.gotrash.api.v1.model.pendingtrashhistory.ClaimPendingTrashHistory;
import com.gotrash.api.v1.model.pendingtrashhistory.PendingTrashHistory;
import com.gotrash.api.v1.model.Trash;
import com.gotrash.api.v1.model.TrashBin;
import com.gotrash.api.v1.transformer.pendingtrashhistory.PendingTrashHistoryTransformer;
import com.gotrash.api.v1.transformer.trashhistory.TrashHistoryTransformer;
import com.gotrash.constant.PendingTrashHistoryStatus;
import com.gotrash.entity.PendingTrashHistoryEntity;
import com.gotrash.repository.PendingTrashHistoryRepository;
import com.gotrash.util.CalculatorUtil;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PendingTrashHistoryService {

  private final PendingTrashHistoryRepository pendingTrashHistoryRepository;
  private final TrashService trashService;
  private final TrashBinService trashBinService;
  private final TrashHistoryService trashHistoryService;
  private final NotificationService notificationService;

  @Transactional
  public void save(PendingTrashHistory pendingTrashHistory) {

    Trash trash = trashService.getTrashByTrashName(pendingTrashHistory.getTrash().getName());
    TrashBin trashBin = trashBinService.getTrashBinByTrashBinId(pendingTrashHistory.getTrashBin().getTrashBinId());

    pendingTrashHistory.setTrash(trash);
    pendingTrashHistory.setTrashBin(trashBin);
    pendingTrashHistory.setStatus(PendingTrashHistoryStatus.NOT_CLAIMED);

    pendingTrashHistoryRepository.save(PendingTrashHistoryTransformer.transformModelToEntity(pendingTrashHistory));
  }

  @Transactional
  public List<PendingTrashHistory> getAllPendingTrashHistory(String trashBinId, PendingTrashHistoryStatus status) {

    List<PendingTrashHistoryEntity> pendingTrashHistoryEntities = pendingTrashHistoryRepository.findAllByTrashBin_TrashBinIdAndStatus(
        UUID.fromString(trashBinId),
        status
    );

    return pendingTrashHistoryEntities
        .stream()
        .map(PendingTrashHistoryTransformer::transformEntityToModel)
        .toList();
  }

  @Transactional
  public ClaimPendingTrashHistory claimPendingTrashHistoryByTrashBinId(String citizenId, String trashBinId) {

    List<PendingTrashHistoryEntity> pendingTrashHistoryEntities = pendingTrashHistoryRepository.findAllByTrashBin_TrashBinIdAndStatus(
        UUID.fromString(trashBinId),
        PendingTrashHistoryStatus.NOT_CLAIMED
    );

    BigInteger totalCoin = BigInteger.valueOf(0);
    BigDecimal totalWeight = BigDecimal.valueOf(0);
    BigInteger totalRating = BigInteger.valueOf(0);

    for (PendingTrashHistoryEntity pendingTrashHistoryEntity : pendingTrashHistoryEntities) {
      totalCoin = totalCoin.add(
          CalculatorUtil.calculateCoin(
              pendingTrashHistoryEntity.getWeight(),
              pendingTrashHistoryEntity.getTrash().getCoin()
          )
      );
      totalWeight = totalWeight.add(pendingTrashHistoryEntity.getWeight());
      totalRating = totalRating.add(
          CalculatorUtil.calculateRating(
              pendingTrashHistoryEntity.getWeight(),
              pendingTrashHistoryEntity.getTrash().getRating()
          )
      );

      pendingTrashHistoryEntity.setStatus(PendingTrashHistoryStatus.CLAIMED);

      PendingTrashHistory pendingTrashHistory = PendingTrashHistoryTransformer.transformEntityToModel(
          pendingTrashHistoryEntity
      );

      // Persist data to trash history table
      trashHistoryService.save(
          TrashHistoryTransformer.transformPendingTrashHistoryToTrashHistory(
              pendingTrashHistory, citizenId
          )
      );
    }

    // Send Notification
    Notification notification = Notification.builder()
        .user(User.builder().userId(citizenId).build())
        .title("Trash Reward Successfully Claimed")
        .description("Your trash history has been claimed. You earned " + totalCoin + " coins, " + totalRating + " ratings, and contributed " + totalWeight + " grams of trash")
        .build();

    notificationService.save(notification);

    pendingTrashHistoryRepository.saveAll(pendingTrashHistoryEntities);

    return ClaimPendingTrashHistory.builder()
        .totalCoin(totalCoin)
        .totalRating(totalRating)
        .totalWeight(totalWeight)
        .build();
  }
}
