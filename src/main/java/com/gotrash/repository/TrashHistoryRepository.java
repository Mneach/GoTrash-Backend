package com.gotrash.repository;

import com.gotrash.entity.NotificationEntity;
import com.gotrash.entity.TrashHistoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface TrashHistoryRepository extends JpaRepository<TrashHistoryEntity, UUID> {
    List<TrashHistoryEntity> findAllByUser_UserId(UUID userId);
}
