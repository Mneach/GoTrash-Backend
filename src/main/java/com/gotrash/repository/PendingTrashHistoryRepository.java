package com.gotrash.repository;

import com.gotrash.constant.PendingTrashHistoryStatus;
import com.gotrash.entity.PendingTrashHistoryEntity;
import com.gotrash.entity.RewardCategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface PendingTrashHistoryRepository extends JpaRepository<PendingTrashHistoryEntity, UUID> {

  List<PendingTrashHistoryEntity> findAllByTrashBin_TrashBinIdAndStatus(UUID trashBinId, PendingTrashHistoryStatus status);
}
