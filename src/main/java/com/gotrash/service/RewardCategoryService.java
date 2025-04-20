package com.gotrash.service;

import com.gotrash.api.v1.model.Notification;
import com.gotrash.api.v1.model.RewardCategory;
import com.gotrash.api.v1.transformer.NotificationTransformer;
import com.gotrash.api.v1.transformer.RewardCategoryTransformer;
import com.gotrash.entity.NotificationEntity;
import com.gotrash.entity.RewardCategoryEntity;
import com.gotrash.repository.RewardCategoryRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RewardCategoryService {
    
    private final RewardCategoryRepository rewardCategoryRepository;

    @Transactional
    public RewardCategory save(RewardCategory rewardCategory) {
        RewardCategoryEntity rewardCategoryEntity = RewardCategoryTransformer.transformModelToEntity(rewardCategory);
        return RewardCategoryTransformer.transformEntityToModel(
                rewardCategoryRepository.save(rewardCategoryEntity)
        );
    }

    public List<RewardCategory> getRewardCategories() {
        List<RewardCategoryEntity> rewardCategoryEntities = rewardCategoryRepository.findAll();

        return rewardCategoryEntities.stream()
            .map(RewardCategoryTransformer::transformEntityToModel)
            .toList();
    }

    public RewardCategory getRewardCategoryByRewardCategoryId(String rewardCategoryId) {

        Optional<RewardCategoryEntity> rewardCategoryEntityOptional = rewardCategoryRepository.findById(UUID.fromString(rewardCategoryId));

        if (rewardCategoryEntityOptional.isEmpty()) {
            throw new EntityNotFoundException("Reward Category with ID " + rewardCategoryId + " Not Found");
        }

        return RewardCategoryTransformer.transformEntityToModel(rewardCategoryEntityOptional.get());
    }

    @Transactional
    public RewardCategory update(RewardCategory rewardCategory) {

        if (!rewardCategoryRepository.existsById(UUID.fromString(rewardCategory.getRewardCategoryId()))) {
            throw new EntityNotFoundException("Reward Category with ID " + rewardCategory.getRewardCategoryId() + " Not Found");
        }

        RewardCategoryEntity rewardCategoryEntity = RewardCategoryTransformer.transformModelToEntity(rewardCategory);
        return RewardCategoryTransformer.transformEntityToModel(
                rewardCategoryRepository.save(rewardCategoryEntity)
        );
    }

    @Transactional
    public void delete(String rewardCategoryId) {
        if (!rewardCategoryRepository.existsById(UUID.fromString(rewardCategoryId))) {
            throw new EntityNotFoundException("Reward Category with ID " + rewardCategoryId + " Not Found");
        }

        rewardCategoryRepository.deleteById(UUID.fromString(rewardCategoryId));
    }
}
