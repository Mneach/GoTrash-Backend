package com.gotrash.service;

import com.gotrash.api.v1.model.TrashBin;
import com.gotrash.api.v1.transformer.TrashBinTransformer;
import com.gotrash.entity.TrashBinEntity;
import com.gotrash.repository.TrashBinRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TrashBinService {

    private final TrashBinRepository trashBinRepository;

    public TrashBin save(TrashBin trashBin) {
        TrashBinEntity trashBinEntity = TrashBinTransformer.transformModelToEntity(trashBin);
        return TrashBinTransformer.transformEntityToModel(
                trashBinRepository.save(trashBinEntity)
        );
    }

    public TrashBin getTrashBinByTrashBinId(String trashBinId) {

        Optional<TrashBinEntity> trashCategoryEntityOptional = trashBinRepository.findById(UUID.fromString(trashBinId));

        if (trashCategoryEntityOptional.isEmpty()) {
            throw new EntityNotFoundException("Trash Bin with ID " + trashBinId + " Not Found");
        }

        return TrashBinTransformer.transformEntityToModel(trashCategoryEntityOptional.get());
    }

    public TrashBin update(TrashBin trashBin) {

        if (!trashBinRepository.existsById(UUID.fromString(trashBin.getTrashBinId()))) {
            throw new EntityNotFoundException("Trash Bin with ID " + trashBin.getTrashBinId() + " Not Found");
        }

        TrashBinEntity trashBinEntity = TrashBinTransformer.transformModelToEntity(trashBin);
        return TrashBinTransformer.transformEntityToModel(
                trashBinRepository.save(trashBinEntity)
        );
    }

    public void delete(String trashBinId) {
        if (!trashBinRepository.existsById(UUID.fromString(trashBinId))) {
            throw new EntityNotFoundException("Trash Bin with ID " + trashBinId + " Not Found");
        }

        trashBinRepository.deleteById(UUID.fromString(trashBinId));
    }
}
