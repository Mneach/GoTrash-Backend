package com.gotrash.repository;

import com.gotrash.entity.CitizenAddressEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CitizenAddressRepository extends JpaRepository<CitizenAddressEntity, UUID> {

  Optional<CitizenAddressEntity> findByIdAndCitizen_UserId(UUID citizenAddressId, UUID citizenId);

  List<CitizenAddressEntity> findAllByCitizen_UserId(UUID citizenId);
}
