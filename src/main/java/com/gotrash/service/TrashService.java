package com.gotrash.service;

import com.gotrash.api.v1.model.Trash;
import com.gotrash.api.v1.model.TrashCategory;
import com.gotrash.api.v1.transformer.TrashTransformer;
import com.gotrash.entity.TrashEntity;
import com.gotrash.exception.rest.BadRequestException;
import com.gotrash.repository.TrashRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TrashService {

    private final TrashRepository trashRepository;
    private final TrashCategoryService trashCategoryService;

    @Transactional
    public Trash save(Trash trash) {

        Optional<TrashEntity> trashEntityOptional = trashRepository.findByName(trash.getName());

        if (trashEntityOptional.isPresent()) {
            throw new BadRequestException("Trash with name " + trash.getName() + " is already exists");
        }

        TrashCategory trashCategory = trashCategoryService.getTrashCategoryByTrashCategoryId(
                trash.getTrashCategory().getTrashCategoryId()
        );

        trash.setTrashCategory(trashCategory);
        TrashEntity trashEntity = TrashTransformer.transformModelToEntity(trash);
        return TrashTransformer.transformEntityToModel(trashRepository.save(trashEntity));
    }

    public List<Trash> getTrashes() {
        List<TrashEntity> trashEntities = trashRepository.findAll();

        return trashEntities.stream()
            .map(TrashTransformer::transformEntityToModel)
            .toList();
    }

    public Trash getTrashByTrashId(String trashId) {
        Optional<TrashEntity> trashEntityOptional = trashRepository.findById(UUID.fromString(trashId));

        if (trashEntityOptional.isEmpty()) {
            throw new EntityNotFoundException("Trash with ID " + trashId + " Not Found");
        }

        return TrashTransformer.transformEntityToModel(trashEntityOptional.get());
    }

    public Trash getTrashByTrashName(String trashName) {
        Optional<TrashEntity> trashEntityOptional = trashRepository.findByName(trashName);

        if (trashEntityOptional.isEmpty()) {
            throw new EntityNotFoundException("Trash with name " + trashName + " Not Found");
        }

        return TrashTransformer.transformEntityToModel(trashEntityOptional.get());
    }

    @Transactional
    public Trash update(Trash trash) {

        if (!trashRepository.existsById(UUID.fromString(trash.getTrashId()))) {
            throw new EntityNotFoundException("Trash with ID " + trash.getTrashId() + " Not Found");
        }

        TrashCategory trashCategory = trashCategoryService.getTrashCategoryByTrashCategoryId(
                trash.getTrashCategory().getTrashCategoryId()
        );

        trash.setTrashCategory(trashCategory);
        TrashEntity trashEntity = TrashTransformer.transformModelToEntity(trash);
        return TrashTransformer.transformEntityToModel(trashRepository.save(trashEntity));
    }

    @Transactional
    public void delete(String trashId) {
        if (!trashRepository.existsById(UUID.fromString(trashId))) {
            throw new EntityNotFoundException("Trash with ID " + trashId + " Not Found");
        }

        trashRepository.deleteById(UUID.fromString(trashId));
    }
}
