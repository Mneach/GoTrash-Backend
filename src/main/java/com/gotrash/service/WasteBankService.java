package com.gotrash.service;

import com.gotrash.api.v1.model.User;
import com.gotrash.api.v1.model.WasteBank;
import com.gotrash.api.v1.model.dashboard.WasteBankTrashCategorySummary;
import com.gotrash.api.v1.model.dashboard.WasteBankTrashSummary;
import com.gotrash.api.v1.transformer.CitizenTransformer;
import com.gotrash.api.v1.transformer.UserTransformer;
import com.gotrash.api.v1.transformer.WasteBankTransformer;
import com.gotrash.entity.UserEntity;
import com.gotrash.entity.WasteBankEntity;
import com.gotrash.helper.FileUploadHelper;
import com.gotrash.repository.UserRepository;
import com.gotrash.repository.WasteBankRepository;
import com.gotrash.util.AuthorityUtil;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
public class WasteBankService {

  private final WasteBankRepository wasteBankRepository;
  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;
  private final FileUploadHelper fileUploadHelper;

  @Transactional
  public WasteBank save(WasteBank wasteBank) {

    User user = User.builder()
        .email(wasteBank.getEmail())
        .role(wasteBank.getRole())
        .password(passwordEncoder.encode(wasteBank.getPassword()))
        .build();

    UserEntity userEntity = userRepository.save(UserTransformer.transformModelToEntity(user));

    WasteBankEntity wasteBankEntity = WasteBankTransformer.transformModelToEntity(wasteBank);
    wasteBankEntity.setUser(userEntity);
    return WasteBankTransformer.transformEntityToModel(
        wasteBankRepository.save(wasteBankEntity)
    );
  }

  public List<WasteBank> getWasteBanks() {
    List<WasteBankEntity> wasteBankEntities = wasteBankRepository.findAll();

    return wasteBankEntities.stream()
        .map(WasteBankTransformer::transformEntityToModel)
        .toList();
  }

  public List<WasteBank> getAllWasteBankByRegion(String region) {
    List<WasteBankEntity> wasteBankEntities = wasteBankRepository.findAllByRegion(region);

    return wasteBankEntities.stream()
        .map(WasteBankTransformer::transformEntityToModel)
        .toList();
  }

  public WasteBank getWasteBankByUserId(String userId) {
    Optional<WasteBankEntity> trashBinEntityOptional = wasteBankRepository.findByUser_UserId(UUID.fromString(userId));

    if (trashBinEntityOptional.isEmpty()) {
      throw new EntityNotFoundException("Waste Bank with User ID " + userId + " Not Found");
    }

    return WasteBankTransformer.transformEntityToModel(trashBinEntityOptional.get());
  }

  public WasteBank getMe() {
    String userId = AuthorityUtil.getCurrentUserId();
    return getWasteBankByUserId(userId);
  }

  @Transactional
  public WasteBank update(WasteBank wasteBank, MultipartFile imageFile) {

    WasteBankEntity wasteBankEntity = wasteBankRepository.findById(UUID.fromString(wasteBank.getUserId()))
        .orElseThrow(() -> new EntityNotFoundException("Waste Bank with ID " + wasteBank.getUserId() + " not found"));

    UserEntity userEntity = userRepository.findById(UUID.fromString(wasteBank.getUserId()))
        .orElseThrow(() -> new EntityNotFoundException("User with ID " + wasteBank.getUserId() + " not found"));

    userEntity.setEmail(wasteBank.getEmail() != null ? wasteBank.getEmail() : userEntity.getEmail());
    userEntity.setRole(wasteBank.getRole() != null ? wasteBank.getRole() : userEntity.getRole());
    userEntity.setPassword(wasteBank.getPassword() != null ? passwordEncoder.encode(wasteBank.getPassword()) : userEntity.getPassword());

    userEntity = userRepository.save(userEntity);

    wasteBankEntity.setUser(userEntity);
    wasteBankEntity.setName(wasteBank.getName() != null ? wasteBank.getName() : wasteBankEntity.getName());
    wasteBankEntity.setAddress(wasteBank.getAddress() != null ? wasteBank.getAddress() : wasteBankEntity.getAddress());
    wasteBankEntity.setLatitude(wasteBank.getLatitude() != null ? wasteBank.getLatitude() : wasteBankEntity.getLatitude());
    wasteBankEntity.setLongitude(wasteBank.getLongitude() != null ? wasteBank.getLongitude() : wasteBankEntity.getLongitude());
    wasteBankEntity.setImageUrl(wasteBank.getImageUrl() != null ? wasteBank.getImageUrl() : wasteBankEntity.getImageUrl());
    wasteBankEntity.setRegion(wasteBank.getRegion() != null ? wasteBank.getRegion() : wasteBankEntity.getRegion());
    wasteBankEntity.setCoin(wasteBank.getCoin() != null ? wasteBank.getCoin() : wasteBankEntity.getCoin());
    wasteBankEntity.setUpdatedAt(LocalDateTime.now());

    if (imageFile != null && !imageFile.isEmpty()) {
      try {
        String filePath = fileUploadHelper.uploadFile("wastebanks", wasteBank.getEmail(), imageFile, wasteBankEntity.getImageUrl());
        String imageUrl = fileUploadHelper.generateFileUrl(filePath);
        wasteBankEntity.setImageUrl(imageUrl);
      } catch (Exception e) {
        throw new RuntimeException(e.getMessage());
      }
    }

    return WasteBankTransformer.transformEntityToModel(
        wasteBankRepository.save(wasteBankEntity)
    );
  }

  @Transactional
  public void delete(String wasteBankId) {
    if (!wasteBankRepository.existsById(UUID.fromString(wasteBankId))) {
      throw new EntityNotFoundException("Waste Bank with ID " + wasteBankId + " Not Found");
    }

    wasteBankRepository.deleteById(UUID.fromString(wasteBankId));
  }

  public WasteBankTrashSummary getTotalTrashByWasteBankId(String wasteBankId) {
    return wasteBankRepository.sumTrashWeightByWasteBankId(UUID.fromString(wasteBankId));
  }

  public List<WasteBankTrashSummary> getTotalTrashGroupByWasteBank() {
    return wasteBankRepository.sumTrashWeightByGroupByWasteBankId();
  }

  public List<WasteBankTrashCategorySummary> getTotalTrashByWasteBankIdGroupByTrashCategory(String wasteBankId) {
    return wasteBankRepository.sumTrashWeightByWasteBankIdGroupedByCategory(UUID.fromString(wasteBankId));
  }
}
