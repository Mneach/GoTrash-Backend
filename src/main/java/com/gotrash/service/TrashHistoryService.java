package com.gotrash.service;

import com.gotrash.api.v1.model.Trash;
import com.gotrash.api.v1.model.TrashHistory;
import com.gotrash.api.v1.model.User;
import com.gotrash.api.v1.transformer.TrashHistoryTransformer;
import com.gotrash.api.v1.transformer.TrashTransformer;
import com.gotrash.entity.TrashEntity;
import com.gotrash.entity.TrashHistoryEntity;
import com.gotrash.repository.TrashHistoryRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TrashHistoryService {

    private final TrashHistoryRepository trashHistoryRepository;
    private final UserService userService;
    private final TrashService trashService;

    public TrashHistory save(TrashHistory trashHistory) {
        User user = userService.getUserByUserId(trashHistory.getUser().getUserId());
        Trash trash = trashService.getTrashByTrashId(trashHistory.getTrash().getTrashId());
        trashHistory.setUser(user);
        trashHistory.setTrash(trash);

        TrashHistoryEntity trashHistoryEntity = trashHistoryRepository.save(
                TrashHistoryTransformer.transformModelToEntity(trashHistory)
        );

        return TrashHistoryTransformer.transformEntityToModel(trashHistoryEntity);
    }

    public TrashHistory getTrashHistoryByTrashHistoryId(String trashHistoryId) {
        Optional<TrashHistoryEntity> trashHistoryEntityOptional = trashHistoryRepository.findById(UUID.fromString(trashHistoryId));

        if (trashHistoryEntityOptional.isEmpty()) {
            throw new EntityNotFoundException("Trash History with ID " + trashHistoryId + " Not Found");
        }

        return TrashHistoryTransformer.transformEntityToModel(trashHistoryEntityOptional.get());
    }

    public List<TrashHistory> getTrashHistoryByUserId(String userId) {
        List<TrashHistoryEntity> trashHistoryEntities = trashHistoryRepository.findAllByUser_UserId(
                UUID.fromString(userId)
        );

        return trashHistoryEntities
                .stream()
                .map(TrashHistoryTransformer::transformEntityToModel)
                .toList();
    }

    public TrashHistory update(TrashHistory trashHistory) {

        if (!trashHistoryRepository.existsById(UUID.fromString(trashHistory.getTrashHistoryId()))) {
            throw new EntityNotFoundException("Trash History with ID " + trashHistory.getTrashHistoryId() + " Not Found");
        }

        User user = userService.getUserByUserId(trashHistory.getUser().getUserId());
        Trash trash = trashService.getTrashByTrashId(trashHistory.getTrash().getTrashId());
        trashHistory.setUser(user);
        trashHistory.setTrash(trash);

        TrashHistoryEntity trashHistoryEntity = trashHistoryRepository.save(
                TrashHistoryTransformer.transformModelToEntity(trashHistory)
        );

        return TrashHistoryTransformer.transformEntityToModel(trashHistoryEntity);
    }

    public void delete(String trashHistoryId) {
        if (!trashHistoryRepository.existsById(UUID.fromString(trashHistoryId))) {
            throw new EntityNotFoundException("Trash History with ID " + trashHistoryId + " Not Found");
        }

        trashHistoryRepository.deleteById(UUID.fromString(trashHistoryId));
    }
}
