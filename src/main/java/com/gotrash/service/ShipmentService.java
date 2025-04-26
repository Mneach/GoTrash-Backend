package com.gotrash.service;

import com.gotrash.api.v1.model.Shipment;
import com.gotrash.api.v1.transformer.ShipmentTransformer;
import com.gotrash.entity.CompanyEntity;
import com.gotrash.entity.ShipmentEntity;
import com.gotrash.entity.WasteBankEntity;
import com.gotrash.repository.CompanyRepository;
import com.gotrash.repository.ShipmentRepository;
import com.gotrash.repository.WasteBankRepository;
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
  private final WasteBankRepository wasteBankRepository;
  private final CompanyRepository companyRepository;

  @Transactional
  public Shipment save(Shipment shipment) {
    WasteBankEntity wasteBankEntity = wasteBankRepository.findById(UUID.fromString(shipment.getWasteBank().getUserId()))
        .orElseThrow(() -> new EntityNotFoundException("WasteBank not found"));
    CompanyEntity companyEntity = companyRepository.findById(UUID.fromString(shipment.getDestinationCompany().getUserId()))
        .orElseThrow(() -> new EntityNotFoundException("Company not found"));

    ShipmentEntity shipmentEntity = ShipmentTransformer.transformModelToEntity(shipment);
    shipmentEntity.setWasteBank(wasteBankEntity);
    shipmentEntity.setDestinationCompany(companyEntity);

    return ShipmentTransformer.transformEntityToModel(
        shipmentRepository.save(shipmentEntity)
    );
  }

  public List<Shipment> getShipments() {
    List<ShipmentEntity> shipmentEntities = shipmentRepository.findAll();

    return shipmentEntities.stream()
        .map(ShipmentTransformer::transformEntityToModel)
        .toList();
  }

  public Shipment getShipmentByShipmentId(String shipmentId) {

    Optional<ShipmentEntity> shipmentEntityOptional = shipmentRepository.findById(UUID.fromString(shipmentId));

    if (shipmentEntityOptional.isEmpty()) {
      throw new jakarta.persistence.EntityNotFoundException("Trash Bin with ID " + shipmentId + " Not Found");
    }

    return ShipmentTransformer.transformEntityToModel(shipmentEntityOptional.get());
  }

  public List<Shipment> getShipmentFilterByCompanyId(String companyId) {
    List<ShipmentEntity> shipmentEntities = shipmentRepository.findAllByDestinationCompany_UserId(UUID.fromString(companyId));

    return shipmentEntities.stream()
        .map(ShipmentTransformer::transformEntityToModel)
        .toList();
  }

  public List<Shipment> getShipmentFilterByWasteBankId(String wasteBankId) {
    List<ShipmentEntity> shipmentEntities = shipmentRepository.findAllByWasteBank_UserId(UUID.fromString(wasteBankId));

    return shipmentEntities.stream()
        .map(ShipmentTransformer::transformEntityToModel)
        .toList();
  }

  @Transactional
  public Shipment update(Shipment shipment) {

    if (!shipmentRepository.existsById(UUID.fromString(shipment.getShipmentId()))) {
      throw new jakarta.persistence.EntityNotFoundException("Trash Bin with ID " + shipment.getShipmentId() + " Not Found");
    }

    WasteBankEntity wasteBankEntity = wasteBankRepository.findById(UUID.fromString(shipment.getWasteBank().getUserId()))
        .orElseThrow(() -> new EntityNotFoundException("WasteBank not found"));
    CompanyEntity companyEntity = companyRepository.findById(UUID.fromString(shipment.getDestinationCompany().getUserId()))
        .orElseThrow(() -> new EntityNotFoundException("Company not found"));

    ShipmentEntity shipmentEntity = ShipmentTransformer.transformModelToEntity(shipment);
    shipmentEntity.setWasteBank(wasteBankEntity);
    shipmentEntity.setDestinationCompany(companyEntity);


    return ShipmentTransformer.transformEntityToModel(
        shipmentRepository.save(shipmentEntity)
    );
  }

  @Transactional
  public void delete(String shipmentId) {
    if (!shipmentRepository.existsById(UUID.fromString(shipmentId))) {
      throw new EntityNotFoundException("Trash Bin with ID " + shipmentId + " Not Found");
    }

    shipmentRepository.deleteById(UUID.fromString(shipmentId));
  }
}
