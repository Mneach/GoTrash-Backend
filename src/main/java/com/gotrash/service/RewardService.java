package com.gotrash.service;

import com.gotrash.api.v1.model.Notification;
import com.gotrash.api.v1.model.Reward;
import com.gotrash.api.v1.model.RewardCategory;
import com.gotrash.api.v1.transformer.CitizenTransformer;
import com.gotrash.api.v1.transformer.NotificationTransformer;
import com.gotrash.api.v1.transformer.RewardCategoryTransformer;
import com.gotrash.api.v1.transformer.RewardTransformer;
import com.gotrash.entity.NotificationEntity;
import com.gotrash.entity.RewardEntity;
import com.gotrash.helper.FileUploadHelper;
import com.gotrash.repository.RewardRepository;
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
public class RewardService {
    private final RewardRepository rewardRepository;
    private final RewardCategoryService rewardCategoryService;
    private final FileUploadHelper fileUploadHelper;

    @Transactional
    public Reward save(Reward reward, MultipartFile imageFile) {
        RewardCategory trashCategory = rewardCategoryService.getRewardCategoryByRewardCategoryId(
                reward.getRewardCategory().getRewardCategoryId()
        );

        if (imageFile != null && !imageFile.isEmpty()) {
            try {
                String filePath = fileUploadHelper.uploadFile("rewards", reward.getName(), imageFile, null);
                String imageUrl = fileUploadHelper.generateFileUrl(filePath);
                reward.setImageUrl(imageUrl);
            } catch (Exception e) {
                throw new RuntimeException(e.getMessage());
            }
        }

        reward.setRewardCategory(trashCategory);
        RewardEntity trashEntity = RewardTransformer.transformModelToEntity(reward);
        return RewardTransformer.transformEntityToModel(rewardRepository.save(trashEntity));
    }

    public List<Reward> getRewards() {
        List<RewardEntity> rewardEntities = rewardRepository.findAll();

        return rewardEntities.stream()
            .map(RewardTransformer::transformEntityToModel)
            .toList();
    }

    public Reward getRewardByRewardId(String rewardId) {
        Optional<RewardEntity> trashEntityOptional = rewardRepository.findById(UUID.fromString(rewardId));

        if (trashEntityOptional.isEmpty()) {
            throw new EntityNotFoundException("Reward with ID " + rewardId + " Not Found");
        }

        return RewardTransformer.transformEntityToModel(trashEntityOptional.get());
    }

    @Transactional
    public Reward update(Reward reward, MultipartFile imageFile) {

        RewardEntity rewardEntity = rewardRepository.findById(UUID.fromString(reward.getRewardId()))
            .orElseThrow(() -> new EntityNotFoundException("Waste Bank with ID " + reward.getRewardId() + " not found"));

        RewardCategory rewardCategory = rewardCategoryService.getRewardCategoryByRewardCategoryId(
                reward.getRewardCategory().getRewardCategoryId()
        );

        reward.setRewardCategory(rewardCategory);

        rewardEntity.setRewardCategory(reward.getRewardCategory() != null ? RewardCategoryTransformer.transformModelToEntity(rewardCategory) : rewardEntity.getRewardCategory());
        rewardEntity.setName(reward.getName() != null ? reward.getName() : rewardEntity.getName());
        rewardEntity.setCoin(reward.getCoin() != null ? reward.getCoin() : rewardEntity.getCoin());
        rewardEntity.setStock(reward.getStock() != null ? reward.getStock() : rewardEntity.getStock());
        rewardEntity.setDescription(reward.getDescription() != null ? reward.getDescription() : rewardEntity.getDescription());
        rewardEntity.setUpdatedAt(LocalDateTime.now());

        if (imageFile != null && !imageFile.isEmpty()) {
            try {
                String filePath = fileUploadHelper.uploadFile("rewards", reward.getName(), imageFile, rewardEntity.getImageUrl());
                String imageUrl = fileUploadHelper.generateFileUrl(filePath);
                rewardEntity.setImageUrl(imageUrl);
            } catch (Exception e) {
                throw new RuntimeException(e.getMessage());
            }
        }

        return RewardTransformer.transformEntityToModel(rewardRepository.save(rewardEntity));
    }

    @Transactional
    public void delete(String rewardId) {
        if (!rewardRepository.existsById(UUID.fromString(rewardId))) {
            throw new EntityNotFoundException("Reward with ID " + rewardId + " Not Found");
        }

        rewardRepository.deleteById(UUID.fromString(rewardId));
    }
}
