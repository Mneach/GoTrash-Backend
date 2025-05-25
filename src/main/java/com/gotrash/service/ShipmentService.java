package com.gotrash.service;

import com.gotrash.api.v1.model.*;
import com.gotrash.api.v1.transformer.ShipmentTransformer;
import com.gotrash.api.v1.transformer.RewardTransformer;
import com.gotrash.api.v1.transformer.CitizenTransformer;
import com.gotrash.constant.ShipmentStatus;
import com.gotrash.entity.CitizenAddressEntity;
import com.gotrash.entity.CitizenEntity;
import com.gotrash.entity.RewardEntity;
import com.gotrash.entity.ShipmentEntity;
import com.gotrash.exception.rest.BadRequestException;
import com.gotrash.repository.CitizenAddressRepository;
import com.gotrash.repository.CitizenRepository;
import com.gotrash.repository.RewardRepository;
import com.gotrash.repository.ShipmentRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ShipmentService {

  private final ShipmentRepository shipmentRepository;
  private final RewardRepository rewardRepository;
  private final CitizenRepository citizenRepository;
  private final CitizenAddressRepository citizenAddressRepository;

  @Transactional
  public Shipment save(Shipment shipment) {

    RewardEntity rewardEntity = rewardRepository.findById(UUID.fromString(shipment.getReward().getRewardId()))
        .orElseThrow(() -> new EntityNotFoundException("Waste Bank with ID " + shipment.getReward().getRewardId() + " not found"));

    CitizenEntity citizenEntity = citizenRepository.findById(UUID.fromString(shipment.getCitizen().getUserId()))
        .orElseThrow(() -> new EntityNotFoundException("Citizen with ID " + shipment.getCitizen().getUserId() + " not found"));

    CitizenAddressEntity citizenAddressEntity = citizenAddressRepository.findByCitizenAddressIdAndCitizen_UserId(
        UUID.fromString(shipment.getCitizenAddress().getCitizenAddressId()),UUID.fromString(shipment.getCitizen().getUserId())
    ).orElseThrow(() -> new EntityNotFoundException(
        "Address not found with ID: " + shipment.getCitizenAddress().getCitizenAddressId() +
            " for citizen: " + shipment.getCitizen().getUserId()));

    BigInteger totalCoinUsed = rewardEntity.getCoin().multiply(BigInteger.valueOf(shipment.getQuantity()));

    if (rewardEntity.getStock() < shipment.getQuantity()) {
      throw new BadRequestException("Quantity more than reward stock");
    }

    if (citizenEntity.getCoin().compareTo(totalCoinUsed) < 0) {
      throw new BadRequestException("User Coin Is Not Sufficient");
    }

    // decrease user coin
    citizenEntity.setCoin(citizenEntity.getCoin().subtract(totalCoinUsed));

    ShipmentEntity shipmentEntity = ShipmentEntity.builder()
        .reward(rewardEntity)
        .citizen(citizenEntity)
        .citizenAddress(citizenAddressEntity)
        .status(ShipmentStatus.IN_PROGRESS)
        .quantity(shipment.getQuantity())
        .build();

    shipmentEntity = shipmentRepository.save(shipmentEntity);

    return ShipmentTransformer.transformEntityToModel(shipmentEntity);
  }

  public List<Shipment> getShipments() {
    List<ShipmentEntity> shipmentEntities = shipmentRepository.findAll();

    return shipmentEntities.stream()
        .map(ShipmentTransformer::transformEntityToModel)
        .toList();
  }

  public Shipment getShipmentById(String shipmentId) {
    Optional<ShipmentEntity> shipmentEntityOptional = shipmentRepository.findById(UUID.fromString(shipmentId));

    if (shipmentEntityOptional.isEmpty()) {
      throw new EntityNotFoundException("Shipment With ID " + shipmentId + " Not Found");
    }

    return ShipmentTransformer.transformEntityToModel(shipmentEntityOptional.get());
  }

  public List<Shipment> getShipmentByUserId(String userId) {
    List<ShipmentEntity> shipmentEntities = shipmentRepository.findAllByCitizen_UserId(UUID.fromString(userId));

    return shipmentEntities.stream()
        .map(ShipmentTransformer::transformEntityToModel)
        .toList();
  }

  @Transactional
  public Shipment markShipmentAsDelivered(String shipmentId) {
    Optional<ShipmentEntity> shipmentEntityOptional = shipmentRepository.findById(UUID.fromString(shipmentId));

    if (shipmentEntityOptional.isEmpty()) {
      throw new EntityNotFoundException("Shipment With ID " + shipmentId + " Not Found");
    }

    ShipmentEntity shipmentEntity = shipmentEntityOptional.get();
    shipmentEntity.setStatus(ShipmentStatus.DELIVERED);

    // increase wastebank coin
    shipmentEntity.getReward().getWasteBank().setCoin(
        shipmentEntity.getReward().getCoin().multiply(BigInteger.valueOf(shipmentEntity.getQuantity()))
    );

    return ShipmentTransformer.transformEntityToModel(
        shipmentRepository.save(shipmentEntity)
    );
  }

  @Transactional
  public Shipment markShipmentAsInTransit(String shipmentId) {
    Optional<ShipmentEntity> shipmentEntityOptional = shipmentRepository.findById(UUID.fromString(shipmentId));

    if (shipmentEntityOptional.isEmpty()) {
      throw new EntityNotFoundException("Shipment With ID " + shipmentId + " Not Found");
    }

    ShipmentEntity shipmentEntity = shipmentEntityOptional.get();
    shipmentEntity.setStatus(ShipmentStatus.IN_TRANSIT);

    if (shipmentEntity.getReward().getStock() < shipmentEntity.getQuantity()) {
      throw new BadRequestException("Quantity more than reward stock");
    }

    // decrease reward stock
    shipmentEntity.getReward().setStock(
        shipmentEntity.getReward().getStock() - shipmentEntity.getQuantity()
    );

    return ShipmentTransformer.transformEntityToModel(
        shipmentRepository.save(shipmentEntity)
    );
  }

  @Transactional
  public Shipment markShipmentAsCancelled(String shipmentId) {
    Optional<ShipmentEntity> shipmentEntityOptional = shipmentRepository.findById(UUID.fromString(shipmentId));

    if (shipmentEntityOptional.isEmpty()) {
      throw new EntityNotFoundException("Shipment With ID " + shipmentId + " Not Found");
    }

    ShipmentEntity shipmentEntity = shipmentEntityOptional.get();
    shipmentEntity.setStatus(ShipmentStatus.CANCELED);

    BigInteger totalCoinUsed = shipmentEntity.getReward().getCoin().multiply(BigInteger.valueOf(shipmentEntity.getQuantity()));

    // increase user coin
    shipmentEntity.getCitizen().setCoin(
        shipmentEntity.getCitizen().getCoin().add(totalCoinUsed)
    );

    return ShipmentTransformer.transformEntityToModel(
        shipmentRepository.save(shipmentEntity)
    );
  }

  @Transactional
  public Shipment update(Shipment shipment) {
    Optional<ShipmentEntity> shipmentEntityOptional = shipmentRepository.findById(UUID.fromString(shipment.getShipmentId()));

    if (shipmentEntityOptional.isEmpty()) {
      throw new EntityNotFoundException("Shipment With ID " + shipment.getShipmentId() + " Not Found");
    }

    ShipmentEntity shipmentEntity = shipmentEntityOptional.get();

    if (shipment.getReward() != null && shipment.getReward().getRewardId() != null) {

      RewardEntity rewardEntity = rewardRepository.findById(UUID.fromString(shipment.getReward().getRewardId()))
          .orElseThrow(() -> new EntityNotFoundException("Waste Bank with ID " + shipment.getReward().getRewardId() + " not found"));

      shipmentEntity.setReward(rewardEntity);
    }

    if (shipment.getCitizen() != null && shipment.getCitizen().getUserId() != null) {
      CitizenEntity citizenEntity = citizenRepository.findById(UUID.fromString(shipment.getCitizen().getUserId()))
          .orElseThrow(() -> new EntityNotFoundException("Citizen with ID " + shipment.getCitizen().getUserId() + " not found"));

      shipmentEntity.setCitizen(citizenEntity);
    }

    if (shipment.getCitizenAddress() != null && shipment.getCitizenAddress().getCitizenAddressId() != null) {
      CitizenAddressEntity citizenAddressEntity = citizenAddressRepository.findByCitizenAddressIdAndCitizen_UserId(
          UUID.fromString(shipment.getCitizenAddress().getCitizenAddressId()),UUID.fromString(shipment.getCitizen().getUserId())
      ).orElseThrow(() -> new EntityNotFoundException(
          "Address not found with ID: " + shipment.getCitizenAddress().getCitizenAddressId() +
              " for citizen: " + shipment.getCitizen().getUserId()));

      shipmentEntity.setCitizenAddress(citizenAddressEntity);
    }


    shipmentEntity.setStatus(shipment.getStatus() != null ? shipment.getStatus() : shipmentEntity.getStatus());

    shipmentEntity = shipmentRepository.save(shipmentEntity);
    return ShipmentTransformer.transformEntityToModel(shipmentEntity);
  }

  @Transactional
  public void delete(String shipmentId) {
    if (!shipmentRepository.existsById(UUID.fromString(shipmentId))) {
      throw new EntityNotFoundException("Shipment With ID " + shipmentId + " Not Found");
    }

    shipmentRepository.deleteById(UUID.fromString(shipmentId));
  }
}
