package com.gotrash.api.v1.transformer;

import com.gotrash.api.v1.model.TrashBin;
import com.gotrash.api.v1.model.WasteBank;
import com.gotrash.api.v1.request.TrashBinRequest;
import com.gotrash.api.v1.response.TrashBinResponse;
import com.gotrash.entity.TrashBinEntity;

import java.util.UUID;

public class TrashBinTransformer {

    public static TrashBin transformRequestToModel(String trashBinId, TrashBinRequest trashBinRequest) {
        return TrashBin.builder()
            .trashBinId(trashBinId)
            .name(trashBinRequest.getName())
            .wasteBank(WasteBank.builder().userId(trashBinRequest.getWasteBankId()).build())
            .latitude(trashBinRequest.getLatitude())
            .longitude(trashBinRequest.getLongitude())
            .address(trashBinRequest.getAddress())
            .build();
    }

    public static TrashBin transformRequestToModel(TrashBinRequest trashBinRequest) {
        return TrashBin.builder()
                .name(trashBinRequest.getName())
                .wasteBank(WasteBank.builder().userId(trashBinRequest.getWasteBankId()).build())
                .latitude(trashBinRequest.getLatitude())
                .longitude(trashBinRequest.getLongitude())
                .address(trashBinRequest.getAddress())
                .build();
    }

    public static TrashBin transformEntityToModel(TrashBinEntity trashBinEntity) {
        return TrashBin.builder()
                .name(trashBinEntity.getName())
                .trashBinId(trashBinEntity.getTrashBinId().toString())
                .wasteBank(WasteBankTransformer.transformEntityToModel(trashBinEntity.getWasteBank()))
                .latitude(trashBinEntity.getLatitude())
                .longitude(trashBinEntity.getLongitude())
                .address(trashBinEntity.getAddress())
                .imageUrl(trashBinEntity.getImageUrl())
                .createdAt(trashBinEntity.getCreatedAt())
                .updatedAt(trashBinEntity.getUpdatedAt())
                .build();
    }

    public static TrashBinEntity transformModelToEntity(TrashBin trashBin) {
        return TrashBinEntity.builder()
                .trashBinId(trashBin.getTrashBinId() != null ? UUID.fromString(trashBin.getTrashBinId()) : null)
                .name(trashBin.getName())
                .wasteBank(WasteBankTransformer.transformModelToEntity(trashBin.getWasteBank()))
                .latitude(trashBin.getLatitude())
                .longitude(trashBin.getLongitude())
                .address(trashBin.getAddress())
                .imageUrl(trashBin.getImageUrl())
                .build();
    }

    public static TrashBinResponse transformModelToResponse(TrashBin trashBin) {
        return TrashBinResponse.builder()
                .trashBinId(trashBin.getTrashBinId())
                .name(trashBin.getName())
                .wasteBank(WasteBankTransformer.transformModelToResponse(trashBin.getWasteBank()))
                .latitude(trashBin.getLatitude())
                .longitude(trashBin.getLongitude())
                .address(trashBin.getAddress())
                .imageUrl(trashBin.getImageUrl())
                .createdAt(trashBin.getCreatedAt())
                .updatedAt(trashBin.getUpdatedAt())
                .build();
    }
}
