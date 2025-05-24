package com.gotrash.service;

import com.gotrash.api.v1.model.Citizen;
import com.gotrash.api.v1.model.Shipment;
import com.gotrash.api.v1.model.Reward;
import com.gotrash.api.v1.model.User;
import com.gotrash.api.v1.transformer.ShipmentTransformer;
import com.gotrash.api.v1.transformer.RewardTransformer;
import com.gotrash.api.v1.transformer.CitizenTransformer;
import com.gotrash.entity.ShipmentEntity;
import com.gotrash.repository.ShipmentRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ShipmentService {

  private final ShipmentRepository shipmentRepository;
  private final RewardService rewardService;
  private final UserService userService;
  private final CitizenService citizenService;
  
  @Transactional
  public Shipment save(Shipment shipment) {

    Reward reward = rewardService.getRewardByRewardId(shipment.getReward().getRewardId());
    Citizen citizen = citizenService.getCitizenByUserId(shipment.getCitizen().getUserId());

    shipment.setReward(reward);
    shipment.setCitizen(citizen);

    ShipmentEntity shipmentEntity = shipmentRepository.save(ShipmentTransformer.transformModelToEntity(shipment));
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
  public Shipment update(Shipment shipment) {
    Optional<ShipmentEntity> shipmentEntityOptional = shipmentRepository.findById(UUID.fromString(shipment.getShipmentId()));

    if (shipmentEntityOptional.isEmpty()) {
      throw new EntityNotFoundException("Shipment With ID " + shipment.getShipmentId() + " Not Found");
    }

    ShipmentEntity shipmentEntity = shipmentEntityOptional.get();

    if (shipment.getReward() != null && shipment.getReward().getRewardId() != null) {
      Reward reward = rewardService.getRewardByRewardId(shipment.getReward().getRewardId());
      shipmentEntity.setReward(RewardTransformer.transformModelToEntity(reward));
    }

    if (shipment.getCitizen() != null && shipment.getCitizen().getUserId() != null) {
      Citizen citizen = citizenService.getCitizenByUserId(shipment.getCitizen().getUserId());
      shipmentEntity.setCitizen(CitizenTransformer.transformModelToEntity(citizen));
    }

    shipmentEntity.setStatus(shipment.getStatus());
    shipmentEntity.setDescription(shipment.getDescription());

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
