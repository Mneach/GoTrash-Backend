package com.gotrash.service;

import com.gotrash.api.v1.model.TrashBin;
import com.gotrash.api.v1.model.WasteBank;
import com.gotrash.api.v1.transformer.TrashBinTransformer;
import com.gotrash.entity.TrashBinEntity;
import com.gotrash.entity.WasteBankEntity;
import com.gotrash.repository.TrashBinRepository;
import com.gotrash.repository.WasteBankRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TrashBinService {

    private final WasteBankRepository wasteBankRepository;
    private final TrashBinRepository trashBinRepository;

    @Transactional
    public TrashBin save(TrashBin trashBin) {
        // Ensure we have a managed waste bank entity
        WasteBankEntity wasteBankEntity = wasteBankRepository.findById(UUID.fromString(trashBin.getWasteBank().getUserId()))
            .orElseThrow(() -> new EntityNotFoundException("WasteBank Data With ID "+ trashBin.getWasteBank().getUserId() +" Not Found"));

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
    public TrashBin update(TrashBin trashBin) {

        if (!trashBinRepository.existsById(UUID.fromString(trashBin.getTrashBinId()))) {
            throw new EntityNotFoundException("Trash Bin with ID " + trashBin.getTrashBinId() + " Not Found");
        }

        WasteBankEntity wasteBankEntity = wasteBankRepository.findById(UUID.fromString(trashBin.getWasteBank().getUserId()))
            .orElseThrow(() -> new EntityNotFoundException("WasteBank Data With ID "+ trashBin.getWasteBank().getUserId() +" Not Found"));

        TrashBinEntity trashBinEntity = TrashBinTransformer.transformModelToEntity(trashBin);
        trashBinEntity.setWasteBank(wasteBankEntity);

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
