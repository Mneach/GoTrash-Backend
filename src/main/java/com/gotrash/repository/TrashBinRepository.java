package com.gotrash.repository;

import com.gotrash.entity.TrashBinEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface TrashBinRepository extends JpaRepository<TrashBinEntity, UUID> {
}
