package com.gotrash.service;

import com.gotrash.api.v1.model.Reward;
import com.gotrash.api.v1.model.RewardCategory;
import com.gotrash.api.v1.transformer.RewardTransformer;
import com.gotrash.entity.RewardEntity;
import com.gotrash.repository.RewardRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RewardService {
    private final RewardRepository rewardRepository;
    private final RewardCategoryService rewardCategoryService;

    public Reward save(Reward reward) {
        RewardCategory trashCategory = rewardCategoryService.getRewardCategoryByRewardCategoryId(
                reward.getRewardCategory().getRewardCategoryId()
        );

        reward.setRewardCategory(trashCategory);
        RewardEntity trashEntity = RewardTransformer.transformModelToEntity(reward);
        return RewardTransformer.transformEntityToModel(rewardRepository.save(trashEntity));
    }

    public Reward getRewardByRewardId(String rewardId) {
        Optional<RewardEntity> trashEntityOptional = rewardRepository.findById(UUID.fromString(rewardId));

        if (trashEntityOptional.isEmpty()) {
            throw new EntityNotFoundException("Reward with ID " + rewardId + " Not Found");
        }

        return RewardTransformer.transformEntityToModel(trashEntityOptional.get());
    }

    public Reward update(Reward reward) {

        if (!rewardRepository.existsById(UUID.fromString(reward.getRewardId()))) {
            throw new EntityNotFoundException("Reward with ID " + reward.getRewardId() + " Not Found");
        }

        RewardCategory trashCategory = rewardCategoryService.getRewardCategoryByRewardCategoryId(
                reward.getRewardCategory().getRewardCategoryId()
        );

        reward.setRewardCategory(trashCategory);
        RewardEntity trashEntity = RewardTransformer.transformModelToEntity(reward);
        return RewardTransformer.transformEntityToModel(rewardRepository.save(trashEntity));
    }

    public void delete(String rewardId) {
        if (!rewardRepository.existsById(UUID.fromString(rewardId))) {
            throw new EntityNotFoundException("Reward with ID " + rewardId + " Not Found");
        }

        rewardRepository.deleteById(UUID.fromString(rewardId));
    }
}
