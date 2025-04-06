package com.gotrash.repository;

import com.gotrash.entity.TrashEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface TrashRepository extends JpaRepository<TrashEntity, UUID> {
}
