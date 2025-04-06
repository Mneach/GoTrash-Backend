package com.gotrash.api.v1.transformer;

import com.gotrash.api.v1.model.Trash;
import com.gotrash.api.v1.model.TrashHistory;
import com.gotrash.api.v1.model.User;
import com.gotrash.api.v1.request.TrashHistoryRequest;
import com.gotrash.api.v1.response.TrashHistoryResponse;
import com.gotrash.entity.TrashHistoryEntity;

import java.util.UUID;

public class TrashHistoryTransformer {
    public static TrashHistory transformRequestToModel(TrashHistoryRequest trashHistoryRequest) {
        return TrashHistory.builder()
                .trashHistoryId(trashHistoryRequest.getTrashHistoryId())
                .trash(Trash.builder().trashId(trashHistoryRequest.getTrashId()).build())
                .user(User.builder().userId(trashHistoryRequest.getUserId()).build())
                .build();
    }

    public static TrashHistory transformEntityToModel(TrashHistoryEntity trashHistoryEntity) {
        return TrashHistory.builder()
                .trashHistoryId(trashHistoryEntity.getTrashHistoryId().toString())
                .trash(TrashTransformer.transformEntityToModel(trashHistoryEntity.getTrash()))
                .user(UserTransformer.transformEntityToModel(trashHistoryEntity.getUser()))
                .createdAt(trashHistoryEntity.getCreatedAt())
                .updatedAt(trashHistoryEntity.getUpdatedAt())
                .build();
    }

    public static TrashHistoryEntity transformModelToEntity(TrashHistory trashHistory) {
        return TrashHistoryEntity.builder()
                .trashHistoryId(trashHistory.getTrashHistoryId() != null ? UUID.fromString(trashHistory.getTrashHistoryId()) : null)
                .trash(TrashTransformer.transformModelToEntity(trashHistory.getTrash()))
                .user(UserTransformer.transformModelToEntity(trashHistory.getUser()))
                .build();
    }

    public static TrashHistoryResponse transformModelToResponse(TrashHistory trashHistory) {
        return TrashHistoryResponse.builder()
                .trashHistoryId(trashHistory.getTrashHistoryId())
                .trash(trashHistory.getTrash())
                .user(trashHistory.getUser())
                .createdAt(trashHistory.getCreatedAt())
                .updatedAt(trashHistory.getUpdatedAt())
                .build();
    }
}
