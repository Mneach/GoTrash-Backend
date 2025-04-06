package com.gotrash.service;

import com.gotrash.api.v1.model.TrashCategory;
import com.gotrash.api.v1.transformer.TrashCategoryTransformer;
import com.gotrash.entity.TrashCategoryEntity;
import com.gotrash.repository.TrashCategoryRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TrashCategoryService {

    private final TrashCategoryRepository trashCategoryRepository;

    public TrashCategory save(TrashCategory trashCategory) {
        TrashCategoryEntity trashCategoryEntity = TrashCategoryTransformer.transformModelToEntity(trashCategory);
        return TrashCategoryTransformer.transformEntityToModel(
                trashCategoryRepository.save(trashCategoryEntity)
        );
    }

    public TrashCategory getTrashCategoryByTrashCategoryId(String trashCategoryId) {

        Optional<TrashCategoryEntity> trashCategoryEntityOptional = trashCategoryRepository.findById(UUID.fromString(trashCategoryId));

        if (trashCategoryEntityOptional.isEmpty()) {
            throw new EntityNotFoundException("Trash Category with ID " + trashCategoryId + " Not Found");
        }

        return TrashCategoryTransformer.transformEntityToModel(trashCategoryEntityOptional.get());
    }

    public TrashCategory update(TrashCategory trashCategory) {

        if (!trashCategoryRepository.existsById(UUID.fromString(trashCategory.getTrashCategoryId()))) {
            throw new EntityNotFoundException("Trash Category with ID " + trashCategory.getTrashCategoryId() + " Not Found");
        }

        TrashCategoryEntity trashCategoryEntity = TrashCategoryTransformer.transformModelToEntity(trashCategory);
        return TrashCategoryTransformer.transformEntityToModel(
                trashCategoryRepository.save(trashCategoryEntity)
        );
    }

    public void delete(String trashCategoryId) {
        if (!trashCategoryRepository.existsById(UUID.fromString(trashCategoryId))) {
            throw new EntityNotFoundException("Trash Category with ID " + trashCategoryId + " Not Found");
        }

        trashCategoryRepository.deleteById(UUID.fromString(trashCategoryId));
    }
}
