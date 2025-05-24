package com.gotrash.api.v1.transformer.trashhistory;

import com.gotrash.api.v1.model.Citizen;
import com.gotrash.api.v1.model.Trash;
import com.gotrash.api.v1.model.TrashBin;
import com.gotrash.api.v1.model.trashhistory.TrashHistory;
import com.gotrash.api.v1.model.User;
import com.gotrash.api.v1.request.trashhistory.TrashHistoryRequest;
import com.gotrash.api.v1.response.trashhistory.TrashHistoryResponse;
import com.gotrash.api.v1.transformer.TrashBinTransformer;
import com.gotrash.api.v1.transformer.TrashTransformer;
import com.gotrash.api.v1.transformer.CitizenTransformer;
import com.gotrash.entity.TrashHistoryEntity;

import java.math.BigInteger;
import java.util.UUID;

public class TrashHistoryTransformer {

    public static TrashHistory transformRequestToModel(String trashHistoryId, TrashHistoryRequest trashHistoryRequest) {
        return TrashHistory.builder()
            .trashHistoryId(trashHistoryId)
            .trash(Trash.builder().trashId(trashHistoryRequest.getTrashId()).build())
            .citizen(Citizen.builder().userId(trashHistoryRequest.getCitizenId()).build())
            .trashBin(TrashBin.builder().trashBinId(trashHistoryRequest.getTrashBinId()).build())
            .weight(trashHistoryRequest.getWeight())
            .build();
    }

    public static TrashHistory transformRequestToModel(TrashHistoryRequest trashHistoryRequest) {
        return TrashHistory.builder()
                .trash(Trash.builder().trashId(trashHistoryRequest.getTrashId()).build())
                .citizen(Citizen.builder().userId(trashHistoryRequest.getCitizenId()).build())
                .trashBin(TrashBin.builder().trashBinId(trashHistoryRequest.getTrashBinId()).build())
                .weight(trashHistoryRequest.getWeight())
                .build();
    }

    public static TrashHistory transformEntityToModel(TrashHistoryEntity trashHistoryEntity) {
        return TrashHistory.builder()
                .trashHistoryId(trashHistoryEntity.getTrashHistoryId().toString())
                .trash(TrashTransformer.transformEntityToModel(trashHistoryEntity.getTrash()))
                .citizen(CitizenTransformer.transformEntityToModel(trashHistoryEntity.getCitizen()))
                .weight(trashHistoryEntity.getWeight())
                .trashBin(TrashBinTransformer.transformEntityToModel(trashHistoryEntity.getTrashBin()))
                .createdAt(trashHistoryEntity.getCreatedAt())
                .updatedAt(trashHistoryEntity.getUpdatedAt())
                .build();
    }



    public static TrashHistory transformEntityToModel(TrashHistoryEntity trashHistoryEntity, BigInteger totalCoin) {
        return TrashHistory.builder()
            .trashHistoryId(trashHistoryEntity.getTrashHistoryId().toString())
            .trash(TrashTransformer.transformEntityToModel(trashHistoryEntity.getTrash()))
            .citizen(CitizenTransformer.transformEntityToModel(trashHistoryEntity.getCitizen()))
            .trashBin(TrashBinTransformer.transformEntityToModel(trashHistoryEntity.getTrashBin()))
            .weight(trashHistoryEntity.getWeight())
            .totalCoin(totalCoin)
            .createdAt(trashHistoryEntity.getCreatedAt())
            .updatedAt(trashHistoryEntity.getUpdatedAt())
            .build();
    }

    public static TrashHistoryEntity transformModelToEntity(TrashHistory trashHistory) {
        return TrashHistoryEntity.builder()
                .trashHistoryId(trashHistory.getTrashHistoryId() != null ? UUID.fromString(trashHistory.getTrashHistoryId()) : null)
                .trash(TrashTransformer.transformModelToEntity(trashHistory.getTrash()))
                .citizen(CitizenTransformer.transformModelToEntity(trashHistory.getCitizen()))
                .trashBin(TrashBinTransformer.transformModelToEntity(trashHistory.getTrashBin()))
                .weight(trashHistory.getWeight())
                .build();
    }

    public static TrashHistoryResponse transformModelToResponse(TrashHistory trashHistory) {
        return TrashHistoryResponse.builder()
                .trashHistoryId(trashHistory.getTrashHistoryId())
                .trash(TrashTransformer.transformModelToResponse(trashHistory.getTrash()))
                .citizen(CitizenTransformer.transformModelToResponse(trashHistory.getCitizen()))
                .trashBin(TrashBinTransformer.transformModelToResponse(trashHistory.getTrashBin()))
                .weight(trashHistory.getWeight())
                .totalCoin(trashHistory.getTotalCoin())
                .createdAt(trashHistory.getCreatedAt())
                .updatedAt(trashHistory.getUpdatedAt())
                .build();
    }
}
