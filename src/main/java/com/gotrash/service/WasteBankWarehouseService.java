package com.gotrash.service;

import com.gotrash.api.v1.model.WasteBankWarehouse;
import com.gotrash.api.v1.transformer.WasteBankWarehouseTransformer;
import com.gotrash.entity.TrashCategoryEntity;
import com.gotrash.entity.WasteBankEntity;
import com.gotrash.entity.WasteBankWarehouseEntity;
import com.gotrash.entity.id.WasteBankWarehouseId;
import com.gotrash.exception.rest.BadRequestException;
import com.gotrash.exception.rest.EntityNotFoundException;
import com.gotrash.repository.TrashCategoryRepository;
import com.gotrash.repository.WasteBankRepository;
import com.gotrash.repository.WasteBankWarehouseRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class WasteBankWarehouseService {

  private final WasteBankWarehouseRepository wasteBankWarehouseRepository;
  private final WasteBankRepository wasteBankRepository;
  private final TrashCategoryRepository trashCategoryRepository;

  @Transactional
  public WasteBankWarehouse addTrashToWasteBankWarehouse(WasteBankWarehouse wasteBankWarehouse) {

    Optional<WasteBankWarehouseEntity> wasteBankWarehouseEntityOptional = wasteBankWarehouseRepository.findById(
        wasteBankWarehouse.getWasteBankWarehouseId()
    );

    if (wasteBankWarehouseEntityOptional.isPresent()) {
      WasteBankWarehouseEntity wasteBankWarehouseEntity = wasteBankWarehouseEntityOptional.get();
      wasteBankWarehouseEntity.setTotalWeight(wasteBankWarehouseEntity.getTotalWeight().add(wasteBankWarehouse.getTotalWeight()));
      return WasteBankWarehouseTransformer.transformEntityToModel(
          wasteBankWarehouseRepository.save(wasteBankWarehouseEntity)
      );
    } else {
      WasteBankEntity wasteBankEntity = wasteBankRepository.findById(UUID.fromString(wasteBankWarehouse.getWasteBank().getUserId()))
          .orElseThrow(() -> new EntityNotFoundException("WasteBank not found"));

      TrashCategoryEntity trashCategoryEntity = trashCategoryRepository.findById(UUID.fromString(wasteBankWarehouse.getTrashCategory().getTrashCategoryId()))
          .orElseThrow(() -> new EntityNotFoundException("TrashCategory not found"));

      WasteBankWarehouseEntity wasteBankWarehouseEntity = WasteBankWarehouseEntity.builder()
          .wasteBankWarehouseId(
              new WasteBankWarehouseId(
                  wasteBankEntity.getUserId(),
                  trashCategoryEntity.getTrashCategoryId()
              )
          )
          .wasteBankEntity(wasteBankEntity)
          .trashCategoryEntity(trashCategoryEntity)
          .totalWeight(wasteBankWarehouse.getTotalWeight())
          .build();

      return WasteBankWarehouseTransformer.transformEntityToModel(
          wasteBankWarehouseRepository.save(wasteBankWarehouseEntity)
      );
    }
  }

  @Transactional
  public List<WasteBankWarehouse> getWasteBankWarehousesByWasteBankId(String wasteBankId) {
    List<WasteBankWarehouseEntity> wasteBankWarehouseEntities = wasteBankWarehouseRepository.findAllByWasteBankId(UUID.fromString(wasteBankId));

    return wasteBankWarehouseEntities.stream()
        .map(WasteBankWarehouseTransformer::transformEntityToModel)
        .toList();
  }
}
