package com.gotrash.repository;

import com.gotrash.entity.RewardCategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface RewardCategoryRepository extends JpaRepository<RewardCategoryEntity, UUID> {
}
