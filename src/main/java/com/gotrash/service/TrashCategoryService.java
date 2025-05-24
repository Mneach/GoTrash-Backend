package com.gotrash.service;

import com.gotrash.api.v1.model.TrashCategory;
import com.gotrash.api.v1.transformer.TrashCategoryTransformer;
import com.gotrash.entity.TrashCategoryEntity;
import com.gotrash.helper.FileUploadHelper;
import com.gotrash.repository.TrashCategoryRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TrashCategoryService {

    private final TrashCategoryRepository trashCategoryRepository;
    private final FileUploadHelper fileUploadHelper;

    @Transactional
    public TrashCategory save(TrashCategory trashCategory, MultipartFile imageFile) {
        TrashCategoryEntity trashCategoryEntity = TrashCategoryTransformer.transformModelToEntity(trashCategory);

        if (imageFile != null && !imageFile.isEmpty()) {
            try {
                String filePath = fileUploadHelper.uploadFile("trashcategories", trashCategory.getName(), imageFile, null);
                String imageUrl = fileUploadHelper.generateFileUrl(filePath);
                trashCategoryEntity.setImageUrl(imageUrl);
            } catch (Exception e) {
                throw new RuntimeException(e.getMessage());
            }
        }

        return TrashCategoryTransformer.transformEntityToModel(
                trashCategoryRepository.save(trashCategoryEntity)
        );
    }

    public List<TrashCategory> getTrashCategories() {
        List<TrashCategoryEntity> trashCategoryEntities = trashCategoryRepository.findAll();

        return trashCategoryEntities.stream()
            .map(TrashCategoryTransformer::transformEntityToModel)
            .toList();
    }

    public TrashCategory getTrashCategoryByTrashCategoryId(String trashCategoryId) {

        Optional<TrashCategoryEntity> trashCategoryEntityOptional = trashCategoryRepository.findById(UUID.fromString(trashCategoryId));

        if (trashCategoryEntityOptional.isEmpty()) {
            throw new EntityNotFoundException("Trash Category with ID " + trashCategoryId + " Not Found");
        }

        return TrashCategoryTransformer.transformEntityToModel(trashCategoryEntityOptional.get());
    }

    @Transactional
    public TrashCategory update(TrashCategory trashCategory, MultipartFile imageFile) {

        TrashCategoryEntity trashCategoryEntity = trashCategoryRepository.findById(UUID.fromString(trashCategory.getTrashCategoryId()))
            .orElseThrow(() -> new EntityNotFoundException("Trash Category with ID " + trashCategory.getTrashCategoryId() + " not found"));

        trashCategoryEntity.setName(trashCategory.getName() != null ? trashCategory.getName() : trashCategoryEntity.getName());
        trashCategoryEntity.setUpdatedAt(LocalDateTime.now());

        if (imageFile != null && !imageFile.isEmpty()) {
            try {
                String filePath = fileUploadHelper.uploadFile("trashcategories", trashCategory.getName(), imageFile, trashCategory.getImageUrl());
                String imageUrl = fileUploadHelper.generateFileUrl(filePath);
                trashCategoryEntity.setImageUrl(imageUrl);
            } catch (Exception e) {
                throw new RuntimeException(e.getMessage());
            }
        }

        return TrashCategoryTransformer.transformEntityToModel(
                trashCategoryRepository.save(trashCategoryEntity)
        );
    }

    @Transactional
    public void delete(String trashCategoryId) {
        if (!trashCategoryRepository.existsById(UUID.fromString(trashCategoryId))) {
            throw new EntityNotFoundException("Trash Category with ID " + trashCategoryId + " Not Found");
        }

        trashCategoryRepository.deleteById(UUID.fromString(trashCategoryId));
    }
}
