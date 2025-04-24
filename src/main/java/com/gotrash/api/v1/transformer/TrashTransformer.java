package com.gotrash.api.v1.transformer;

import com.gotrash.api.v1.model.Trash;
import com.gotrash.api.v1.model.TrashCategory;
import com.gotrash.api.v1.request.TrashRequest;
import com.gotrash.api.v1.response.TrashResponse;
import com.gotrash.entity.TrashCategoryEntity;
import com.gotrash.entity.TrashEntity;

import java.util.UUID;

public class TrashTransformer {

    public static Trash transformEntityToModel(TrashEntity trashEntity) {
        return Trash.builder()
                .trashId(trashEntity.getTrashId().toString())
                .trashCategory(TrashCategoryTransformer.transformEntityToModel(trashEntity.getTrashCategory()))
                .coin(trashEntity.getCoin())
                .description(trashEntity.getDescription())
                .createdAt(trashEntity.getCreatedAt())
                .updatedAt(trashEntity.getUpdatedAt())
                .build();
    }

    public static TrashEntity transformModelToEntity(Trash trash) {
        return TrashEntity.builder()
                .trashId(trash.getTrashId() != null ? UUID.fromString(trash.getTrashId()) : null)
                .trashCategory(TrashCategoryTransformer.transformModelToEntity(trash.getTrashCategory()))
                .coin(trash.getCoin())
                .description(trash.getDescription())
                .build();
    }

    public static Trash transformRequestToModel(String trashId, TrashRequest trashRequest) {
        return Trash.builder()
                .trashId(trashId)
                .trashCategory(TrashCategory.builder().trashCategoryId(trashRequest.getTrashCategoryId()).build())
                .coin(trashRequest.getCoin())
                .description(trashRequest.getDescription())
                .build();
    }

    public static Trash transformRequestToModel(TrashRequest trashRequest) {
        return Trash.builder()
            .trashCategory(TrashCategory.builder().trashCategoryId(trashRequest.getTrashCategoryId()).build())
            .coin(trashRequest.getCoin())
            .description(trashRequest.getDescription())
            .build();
    }

    public static TrashResponse transformModelToResponse(Trash trash) {
        return TrashResponse.builder()
                .trashId(trash.getTrashId())
                .trashCategory(TrashCategoryTransformer.transformModelToResponse(trash.getTrashCategory()))
                .coin(trash.getCoin())
                .description(trash.getDescription())
                .createdAt(trash.getCreatedAt())
                .updatedAt(trash.getUpdatedAt())
                .build();
    }
}
