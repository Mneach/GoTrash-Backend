package com.gotrash.repository;

import com.gotrash.entity.CitizenEntity;
import com.gotrash.entity.WasteBankEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.math.BigInteger;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CitizenRepository extends JpaRepository<CitizenEntity, UUID> {
  Optional<CitizenEntity> findByUser_UserId(UUID userId);
  Optional<CitizenEntity> findByPhoneNumber(String phoneNumber);

  @Query("SELECT c FROM CitizenEntity c ORDER BY c.rating DESC LIMIT 10")
  List<CitizenEntity> findTop10ByOrderByRatingDesc();
}
