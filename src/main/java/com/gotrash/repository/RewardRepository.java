package com.gotrash.repository;

import com.gotrash.entity.RewardEntity;
import com.gotrash.entity.TrashBinEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface RewardRepository extends JpaRepository<RewardEntity, UUID> {
  List<RewardEntity> findAllByWasteBank_UserId(UUID wasteBankId);
}
