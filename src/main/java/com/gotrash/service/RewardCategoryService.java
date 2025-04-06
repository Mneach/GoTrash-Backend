package com.gotrash.service;

import com.gotrash.api.v1.model.RewardCategory;
import com.gotrash.api.v1.transformer.RewardCategoryTransformer;
import com.gotrash.entity.RewardCategoryEntity;
import com.gotrash.repository.RewardCategoryRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RewardCategoryService {
    
    private final RewardCategoryRepository rewardCategoryRepository;
    public RewardCategory save(RewardCategory rewardCategory) {
        RewardCategoryEntity rewardCategoryEntity = RewardCategoryTransformer.transformModelToEntity(rewardCategory);
        return RewardCategoryTransformer.transformEntityToModel(
                rewardCategoryRepository.save(rewardCategoryEntity)
        );
    }

    public RewardCategory getRewardCategoryByRewardCategoryId(String rewardCategoryId) {

        Optional<RewardCategoryEntity> rewardCategoryEntityOptional = rewardCategoryRepository.findById(UUID.fromString(rewardCategoryId));

        if (rewardCategoryEntityOptional.isEmpty()) {
            throw new EntityNotFoundException("Reward Category with ID " + rewardCategoryId + " Not Found");
        }

        return RewardCategoryTransformer.transformEntityToModel(rewardCategoryEntityOptional.get());
    }

    public RewardCategory update(RewardCategory rewardCategory) {

        if (!rewardCategoryRepository.existsById(UUID.fromString(rewardCategory.getRewardCategoryId()))) {
            throw new EntityNotFoundException("Reward Category with ID " + rewardCategory.getRewardCategoryId() + " Not Found");
        }

        RewardCategoryEntity rewardCategoryEntity = RewardCategoryTransformer.transformModelToEntity(rewardCategory);
        return RewardCategoryTransformer.transformEntityToModel(
                rewardCategoryRepository.save(rewardCategoryEntity)
        );
    }

    public void delete(String rewardCategoryId) {
        if (!rewardCategoryRepository.existsById(UUID.fromString(rewardCategoryId))) {
            throw new EntityNotFoundException("Reward Category with ID " + rewardCategoryId + " Not Found");
        }

        rewardCategoryRepository.deleteById(UUID.fromString(rewardCategoryId));
    }
}
