package com.gotrash.api.v1.transformer.pendingtrashhistory;

import com.gotrash.api.v1.model.pendingtrashhistory.PendingTrashHistory;
import com.gotrash.api.v1.model.Trash;
import com.gotrash.api.v1.model.TrashBin;
import com.gotrash.api.v1.request.PendingTrashHistoryRequest;
import com.gotrash.api.v1.response.pendingtrashhistory.PendingTrashHistoryResponse;
import com.gotrash.api.v1.transformer.TrashBinTransformer;
import com.gotrash.api.v1.transformer.TrashTransformer;
import com.gotrash.entity.PendingTrashHistoryEntity;
import com.gotrash.util.CalculatorUtil;

import java.math.BigInteger;
import java.util.UUID;

public class PendingTrashHistoryTransformer {

    public static PendingTrashHistory transformRequestToModel(String pendingTrashHistoryId, PendingTrashHistoryRequest pendingTrashHistoryRequest) {
        return PendingTrashHistory.builder()
            .pendingTrashHistoryId(pendingTrashHistoryId)
            .trash(Trash.builder().name(pendingTrashHistoryRequest.getTrashName()).build())
            .trashBin(TrashBin.builder().trashBinId(pendingTrashHistoryRequest.getTrashBinId()).build())
            .weight(pendingTrashHistoryRequest.getWeight())
            .build();
    }

    public static PendingTrashHistory transformRequestToModel(PendingTrashHistoryRequest pendingTrashHistoryRequest) {
        return PendingTrashHistory.builder()
                .trash(Trash.builder().name(pendingTrashHistoryRequest.getTrashName()).build())
                .trashBin(TrashBin.builder().trashBinId(pendingTrashHistoryRequest.getTrashBinId()).build())
                .weight(pendingTrashHistoryRequest.getWeight())
                .build();
    }

    public static PendingTrashHistory transformEntityToModel(PendingTrashHistoryEntity pendingTrashHistoryEntity) {
        return PendingTrashHistory.builder()
                .pendingTrashHistoryId(pendingTrashHistoryEntity.getPendingTrashHistoryId().toString())
                .trash(TrashTransformer.transformEntityToModel(pendingTrashHistoryEntity.getTrash()))
                .weight(pendingTrashHistoryEntity.getWeight())
                .trashBin(TrashBinTransformer.transformEntityToModel(pendingTrashHistoryEntity.getTrashBin()))
                .status(pendingTrashHistoryEntity.getStatus())
                .createdAt(pendingTrashHistoryEntity.getCreatedAt())
                .updatedAt(pendingTrashHistoryEntity.getUpdatedAt())
                .build();
    }

    public static PendingTrashHistory transformEntityToModel(PendingTrashHistoryEntity pendingTrashHistoryEntity, BigInteger totalCoin) {
        return PendingTrashHistory.builder()
            .pendingTrashHistoryId(pendingTrashHistoryEntity.getPendingTrashHistoryId().toString())
            .trash(TrashTransformer.transformEntityToModel(pendingTrashHistoryEntity.getTrash()))
            .trashBin(TrashBinTransformer.transformEntityToModel(pendingTrashHistoryEntity.getTrashBin()))
            .weight(pendingTrashHistoryEntity.getWeight())
            .totalCoin(totalCoin)
            .status(pendingTrashHistoryEntity.getStatus())
            .createdAt(pendingTrashHistoryEntity.getCreatedAt())
            .updatedAt(pendingTrashHistoryEntity.getUpdatedAt())
            .build();
    }

    public static PendingTrashHistoryEntity transformModelToEntity(PendingTrashHistory pendingTrashHistory) {
        return PendingTrashHistoryEntity.builder()
                .pendingTrashHistoryId(pendingTrashHistory.getPendingTrashHistoryId() != null ? UUID.fromString(pendingTrashHistory.getPendingTrashHistoryId()) : null)
                .trash(TrashTransformer.transformModelToEntity(pendingTrashHistory.getTrash()))
                .trashBin(TrashBinTransformer.transformModelToEntity(pendingTrashHistory.getTrashBin()))
                .weight(pendingTrashHistory.getWeight())
                .status(pendingTrashHistory.getStatus())
                .build();
    }

    public static PendingTrashHistoryResponse transformModelToResponse(PendingTrashHistory pendingTrashHistory) {
        return PendingTrashHistoryResponse.builder()
                .pendingTrashHistoryId(pendingTrashHistory.getPendingTrashHistoryId())
                .trash(TrashTransformer.transformModelToResponse(pendingTrashHistory.getTrash()))
                .trashBin(TrashBinTransformer.transformModelToResponse(pendingTrashHistory.getTrashBin()))
                .weight(pendingTrashHistory.getWeight())
                .totalCoin(CalculatorUtil.calculateCoin(
                    pendingTrashHistory.getWeight(), pendingTrashHistory.getTrash().getCoin()
                ))
                .status(pendingTrashHistory.getStatus())
                .createdAt(pendingTrashHistory.getCreatedAt())
                .updatedAt(pendingTrashHistory.getUpdatedAt())
                .build();
    }
}
