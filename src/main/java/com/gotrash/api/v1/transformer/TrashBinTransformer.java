package com.gotrash.api.v1.transformer;

import com.gotrash.api.v1.model.TrashBin;
import com.gotrash.api.v1.request.TrashBinRequest;
import com.gotrash.api.v1.response.TrashBinResponse;
import com.gotrash.entity.TrashBinEntity;

import java.util.UUID;

public class TrashBinTransformer {
    public static TrashBin transformRequestToModel(TrashBinRequest trashBinRequest) {
        return TrashBin.builder()
                .trashBinId(trashBinRequest.getTrashBinId())
                .latitude(trashBinRequest.getLatitude())
                .longitude(trashBinRequest.getLongitude())
                .address(trashBinRequest.getAddress())
                .imageName(trashBinRequest.getImageName())
                .imageUrl(trashBinRequest.getImageUrl())
                .build();
    }

    public static TrashBin transformEntityToModel(TrashBinEntity trashBinEntity) {
        return TrashBin.builder()
                .trashBinId(trashBinEntity.getTrashBinId().toString())
                .latitude(trashBinEntity.getLatitude())
                .longitude(trashBinEntity.getLongitude())
                .address(trashBinEntity.getAddress())
                .imageName(trashBinEntity.getImageName())
                .imageUrl(trashBinEntity.getImageUrl())
                .createdAt(trashBinEntity.getCreatedAt())
                .updatedAt(trashBinEntity.getUpdatedAt())
                .build();
    }

    public static TrashBinEntity transformModelToEntity(TrashBin trashBin) {
        return TrashBinEntity.builder()
                .trashBinId(trashBin.getTrashBinId() != null ? UUID.fromString(trashBin.getTrashBinId()) : null)
                .latitude(trashBin.getLatitude())
                .longitude(trashBin.getLongitude())
                .address(trashBin.getAddress())
                .imageName(trashBin.getImageName())
                .imageUrl(trashBin.getImageUrl())
                .build();
    }

    public static TrashBinResponse transformModelToResponse(TrashBin trashBin) {
        return TrashBinResponse.builder()
                .trashBinId(trashBin.getTrashBinId())
                .latitude(trashBin.getLatitude())
                .longitude(trashBin.getLongitude())
                .address(trashBin.getAddress())
                .imageName(trashBin.getImageName())
                .imageUrl(trashBin.getImageUrl())
                .createdAt(trashBin.getCreatedAt())
                .updatedAt(trashBin.getUpdatedAt())
                .build();
    }
}
