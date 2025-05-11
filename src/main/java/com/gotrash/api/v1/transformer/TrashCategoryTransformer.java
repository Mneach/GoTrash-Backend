package com.gotrash.api.v1.transformer;

import com.gotrash.api.v1.model.TrashCategory;
import com.gotrash.api.v1.request.TrashCategoryRequest;
import com.gotrash.api.v1.response.TrashCategoryResponse;
import com.gotrash.entity.TrashCategoryEntity;

import java.util.UUID;

public class TrashCategoryTransformer {

    public static TrashCategory transformRequestToModel(String trashCategoryId, TrashCategoryRequest trashCategoryRequest) {
        return TrashCategory.builder()
            .trashCategoryId(trashCategoryId)
            .name(trashCategoryRequest.getName())
            .build();
    }

    public static TrashCategory transformRequestToModel(TrashCategoryRequest trashCategoryRequest) {
        return TrashCategory.builder()
                .name(trashCategoryRequest.getName())
                .build();
    }

    public static TrashCategory transformEntityToModel(TrashCategoryEntity trashCategoryEntity) {
        return TrashCategory.builder()
                .trashCategoryId(trashCategoryEntity.getTrashCategoryId().toString())
                .name(trashCategoryEntity.getName())
                .imageUrl(trashCategoryEntity.getImageUrl())
                .createdAt(trashCategoryEntity.getCreatedAt())
                .updatedAt(trashCategoryEntity.getUpdatedAt())
                .build();
    }

    public static TrashCategoryEntity transformModelToEntity(TrashCategory trashCategory) {
        return TrashCategoryEntity.builder()
                .trashCategoryId(trashCategory.getTrashCategoryId() != null ? UUID.fromString(trashCategory.getTrashCategoryId()) : null)
                .name(trashCategory.getName())
                .imageUrl(trashCategory.getImageUrl())
                .createdAt(trashCategory.getCreatedAt())
                .updatedAt(trashCategory.getUpdatedAt())
                .build();
    }

    public static TrashCategoryResponse transformModelToResponse(TrashCategory trashCategory) {
        return TrashCategoryResponse.builder()
                .trashCategoryId(trashCategory.getTrashCategoryId())
                .name(trashCategory.getName())
                .imageUrl(trashCategory.getImageUrl())
                .createdAt(trashCategory.getCreatedAt())
                .updatedAt(trashCategory.getUpdatedAt())
                .build();
    }
}
