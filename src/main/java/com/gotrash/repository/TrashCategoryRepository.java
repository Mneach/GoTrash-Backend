package com.gotrash.repository;

import com.gotrash.entity.TrashCategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface TrashCategoryRepository extends JpaRepository<TrashCategoryEntity, UUID> {
}
