package com.gotrash.service;

import com.gotrash.api.v1.model.Citizen;
import com.gotrash.api.v1.model.CitizenAddress;
import com.gotrash.api.v1.transformer.CitizenAddressTransformer;
import com.gotrash.api.v1.transformer.CitizenTransformer;
import com.gotrash.entity.CitizenAddressEntity;
import com.gotrash.entity.CitizenEntity;
import com.gotrash.repository.CitizenAddressRepository;
import com.gotrash.repository.CitizenRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CitizenAddressService {

  private final CitizenAddressRepository citizenAddressRepository;
  private final CitizenRepository citizenRepository;

  @Transactional
  public CitizenAddress save(CitizenAddress citizenAddress) {
    CitizenAddressEntity citizenAddressEntity = CitizenAddressTransformer.transformModelToEntity(citizenAddress);

    CitizenEntity citizenEntity = citizenRepository.findByUser_UserId(UUID.fromString(citizenAddress.getCitizen().getUserId()))
        .orElseThrow(() -> new EntityNotFoundException("Citizen not found with user ID: " + citizenAddress.getCitizen().getUserId()));

    citizenAddressEntity.setCitizen(citizenEntity);
    return CitizenAddressTransformer.transformEntityToModel(
        citizenAddressRepository.save(citizenAddressEntity)
    );
  }

  @Transactional
  public List<CitizenAddress> getAllAddressesByCitizenId(String citizenId) {

    List<CitizenAddressEntity> citizenAddressEntities = citizenAddressRepository.findAllByCitizen_UserId(
        UUID.fromString(citizenId)
    );

    return citizenAddressEntities
        .stream()
        .map(CitizenAddressTransformer::transformEntityToModel)
        .toList();
  }

  @Transactional
  public CitizenAddress update(CitizenAddress citizenAddress) {

    CitizenAddressEntity citizenAddressEntity = citizenAddressRepository.findByCitizenAddressIdAndCitizen_UserId(
        UUID.fromString(citizenAddress.getCitizenAddressId()),UUID.fromString(citizenAddress.getCitizen().getUserId())
    ).orElseThrow(() -> new EntityNotFoundException("Address not found with ID: " + citizenAddress.getCitizenAddressId() + " for citizen: " + citizenAddress.getCitizen().getUserId()));

    citizenAddressEntity.setAddress(citizenAddress.getAddress() != null ? citizenAddress.getAddress() : citizenAddressEntity.getAddress());
    citizenAddressEntity.setLabel(citizenAddress.getLabel() != null ? citizenAddress.getLabel() : citizenAddressEntity.getLabel());
    citizenAddressEntity.setNote(citizenAddress.getNote() != null ? citizenAddress.getNote() : citizenAddressEntity.getNote());
    citizenAddressEntity.setUpdatedAt(LocalDateTime.now());

    return CitizenAddressTransformer.transformEntityToModel(
        citizenAddressRepository.save(citizenAddressEntity)
    );
  }

  @Transactional
  public CitizenAddress getCitizenAddressByCitizenIdAndAddressId(String citizenId, String citizenAddressId) {

    CitizenAddressEntity citizenAddressEntity = citizenAddressRepository.findByCitizenAddressIdAndCitizen_UserId(
            UUID.fromString(citizenAddressId),UUID.fromString(citizenId)
    ).orElseThrow(() -> new EntityNotFoundException("Address not found with ID: " + citizenAddressId + " for citizen: " + citizenId));

    return CitizenAddressTransformer.transformEntityToModel(
        citizenAddressEntity
    );
  }

  public void delete(String citizenId, String citizenAddressId) {

    CitizenAddressEntity citizenAddressEntity = citizenAddressRepository.findByCitizenAddressIdAndCitizen_UserId(
        UUID.fromString(citizenAddressId),UUID.fromString(citizenId)
    ).orElseThrow(() -> new EntityNotFoundException("Address not found with ID: " + citizenAddressId + " for citizen: " + citizenId));

    citizenAddressRepository.delete(citizenAddressEntity);
  }
}
