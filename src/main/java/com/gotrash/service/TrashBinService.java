package com.gotrash.service;

import com.gotrash.api.v1.model.TrashBin;
import com.gotrash.api.v1.model.WasteBank;
import com.gotrash.api.v1.transformer.TrashBinTransformer;
import com.gotrash.entity.TrashBinEntity;
import com.gotrash.entity.WasteBankEntity;
import com.gotrash.helper.FileUploadHelper;
import com.gotrash.repository.TrashBinRepository;
import com.gotrash.repository.WasteBankRepository;
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
public class TrashBinService {

    private final WasteBankRepository wasteBankRepository;
    private final TrashBinRepository trashBinRepository;
    private final FileUploadHelper fileUploadHelper;

    @Transactional
    public TrashBin save(TrashBin trashBin, MultipartFile imageFile) {
        // Ensure we have a managed waste bank entity
        WasteBankEntity wasteBankEntity = wasteBankRepository.findById(UUID.fromString(trashBin.getWasteBank().getUserId()))
            .orElseThrow(() -> new EntityNotFoundException("WasteBank Data With ID "+ trashBin.getWasteBank().getUserId() +" Not Found"));

        if (imageFile != null && !imageFile.isEmpty()) {
            try {
                String filePath = fileUploadHelper.uploadFile("trashbin", trashBin.getName(), imageFile, null);
                String imageUrl = fileUploadHelper.generateFileUrl(filePath);
                trashBin.setImageUrl(imageUrl);
            } catch (Exception e) {
                throw new RuntimeException(e.getMessage());
            }
        }

        TrashBinEntity trashBinEntity = TrashBinTransformer.transformModelToEntity(trashBin);
        trashBinEntity.setWasteBank(wasteBankEntity);
        return TrashBinTransformer.transformEntityToModel(
                trashBinRepository.save(trashBinEntity)
        );
    }

    public List<TrashBin> getTrashBins() {
        List<TrashBinEntity> trashBinEntities = trashBinRepository.findAll();

        return trashBinEntities.stream()
            .map(TrashBinTransformer::transformEntityToModel)
            .toList();
    }

    public TrashBin getTrashBinByTrashBinId(String trashBinId) {

        Optional<TrashBinEntity> trashBinEntityOptional = trashBinRepository.findById(UUID.fromString(trashBinId));

        if (trashBinEntityOptional.isEmpty()) {
            throw new EntityNotFoundException("Trash Bin with ID " + trashBinId + " Not Found");
        }

        return TrashBinTransformer.transformEntityToModel(trashBinEntityOptional.get());
    }

    public List<TrashBin> getTrashBinFilterByWasteBankId(String wasteBankId) {
            List<TrashBinEntity> trashBinEntities = trashBinRepository.findAllByWasteBank_UserId(UUID.fromString(wasteBankId));

        return trashBinEntities.stream()
            .map(TrashBinTransformer::transformEntityToModel)
            .toList();
    }

    @Transactional
    public TrashBin update(TrashBin trashBin, MultipartFile imageFile) {

        if (!trashBinRepository.existsById(UUID.fromString(trashBin.getTrashBinId()))) {
            throw new EntityNotFoundException("Trash Bin with ID " + trashBin.getTrashBinId() + " Not Found");
        }

        TrashBinEntity trashBinEntity = TrashBinTransformer.transformModelToEntity(trashBin);

        if (trashBin.getWasteBank().getUserId() != null) {
            WasteBankEntity wasteBankEntity = wasteBankRepository.findById(UUID.fromString(trashBin.getWasteBank().getUserId()))
                .orElseThrow(() -> new EntityNotFoundException("WasteBank Data With ID "+ trashBin.getWasteBank().getUserId() +" Not Found"));

            trashBinEntity.setWasteBank(trashBin.getWasteBank().getUserId() != null ? wasteBankEntity : trashBinEntity.getWasteBank());
        }

        trashBinEntity.setName(trashBin.getName() != null ? trashBin.getName() : trashBinEntity.getName());
        trashBinEntity.setAddress(trashBin.getAddress() != null ? trashBin.getAddress() : trashBinEntity.getAddress());
        trashBinEntity.setLatitude(trashBin.getLatitude() != null ? trashBin.getLatitude() : trashBinEntity.getLatitude());
        trashBinEntity.setLongitude(trashBin.getLongitude() != null ? trashBin.getLongitude() : trashBinEntity.getLongitude());
        trashBinEntity.setUpdatedAt(LocalDateTime.now());

        if (imageFile != null && !imageFile.isEmpty()) {
            try {
                String filePath = fileUploadHelper.uploadFile("trashbin", trashBin.getName(), imageFile, trashBinEntity.getImageUrl());
                String imageUrl = fileUploadHelper.generateFileUrl(filePath);
                trashBinEntity.setImageUrl(imageUrl);
            } catch (Exception e) {
                throw new RuntimeException(e.getMessage());
            }
        }

        return TrashBinTransformer.transformEntityToModel(
                trashBinRepository.save(trashBinEntity)
        );
    }

    @Transactional
    public void delete(String trashBinId) {
        if (!trashBinRepository.existsById(UUID.fromString(trashBinId))) {
            throw new EntityNotFoundException("Trash Bin with ID " + trashBinId + " Not Found");
        }

        trashBinRepository.deleteById(UUID.fromString(trashBinId));
    }
}
