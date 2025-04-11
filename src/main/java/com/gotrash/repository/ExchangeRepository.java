package com.gotrash.repository;

import com.gotrash.entity.ExchangeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.NativeQuery;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ExchangeRepository extends JpaRepository<ExchangeEntity, UUID> {
  List<ExchangeEntity> findAllByUser_UserId(UUID userId);
}
