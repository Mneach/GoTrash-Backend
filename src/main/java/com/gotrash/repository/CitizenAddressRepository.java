package com.gotrash.repository;

import com.gotrash.entity.CitizenAddressEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CitizenAddressRepository extends JpaRepository<CitizenAddressEntity, UUID> {

  Optional<CitizenAddressEntity> findByCitizenAddressIdAndCitizen_UserId(UUID citizenAddressId, UUID citizenId);

  List<CitizenAddressEntity> findAllByCitizen_UserId(UUID citizenId);
}
