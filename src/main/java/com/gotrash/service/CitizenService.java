package com.gotrash.service;

import com.gotrash.api.v1.model.Citizen;
import com.gotrash.api.v1.model.User;
import com.gotrash.api.v1.transformer.CitizenTransformer;
import com.gotrash.api.v1.transformer.UserTransformer;
import com.gotrash.entity.CitizenEntity;
import com.gotrash.entity.UserEntity;
import com.gotrash.exception.rest.BadRequestException;
import com.gotrash.helper.FileUploadHelper;
import com.gotrash.repository.CitizenRepository;
import com.gotrash.repository.UserRepository;
import com.gotrash.util.AuthorityUtil;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigInteger;
import java.util.List;
import java.util.Optional;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
@AllArgsConstructor
public class CitizenService {

  private final CitizenRepository citizenRepository;
  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;
  private final FileUploadHelper fileUploadHelper;

  @Transactional
  public Citizen save(Citizen citizen) {

    User user = User.builder()
        .email(citizen.getEmail())
        .role(citizen.getRole())
        .password(passwordEncoder.encode(citizen.getPassword()))
        .build();

    UserEntity userEntity = userRepository.save(UserTransformer.transformModelToEntity(user));

    CitizenEntity citizenEntity = CitizenTransformer.transformModelToEntity(citizen);
    citizenEntity.setUser(userEntity);

    return CitizenTransformer.transformEntityToModel(
        citizenRepository.save(citizenEntity)
    );
  }

  public List<Citizen> getCitizens() {
    List<CitizenEntity> citizenEntities = citizenRepository.findAll();

    return citizenEntities.stream()
        .map(CitizenTransformer::transformEntityToModel)
        .toList();
  }

  public List<Citizen> getCitizensByIds(List<UUID> citizenIds) {
    List<CitizenEntity> citizenEntities = citizenRepository.findAllById(citizenIds);

    return citizenEntities.stream()
        .map(CitizenTransformer::transformEntityToModel)
        .toList();
  }

  public Citizen getCitizenByUserId(String userId) {
    Optional<CitizenEntity> citizenEntityOptional = citizenRepository.findByUser_UserId(UUID.fromString(userId));

    if (citizenEntityOptional.isEmpty()) {
      throw new EntityNotFoundException("Citizen with User ID " + userId + " Not Found");
    }

    return CitizenTransformer.transformEntityToModel(citizenEntityOptional.get());
  }

  public Citizen getMe() {
    String userId = AuthorityUtil.getCurrentUserId();
    return getCitizenByUserId(userId);
  }

  @Transactional
  public Citizen update(Citizen citizen, MultipartFile imageFile) {

    CitizenEntity citizenEntity = citizenRepository.findById(UUID.fromString(citizen.getUserId()))
        .orElseThrow(() -> new EntityNotFoundException("Citizen with ID " + citizen.getUserId() + " not found"));

    UserEntity userEntity = userRepository.findById(UUID.fromString(citizen.getUserId()))
        .orElseThrow(() -> new EntityNotFoundException("User with ID " + citizen.getUserId() + " not found"));

    userEntity.setEmail(citizen.getEmail() != null ? citizen.getEmail() : userEntity.getEmail());
    userEntity.setRole(citizen.getRole() != null ? citizen.getRole() : userEntity.getRole());
    userEntity.setPassword(citizen.getPassword() != null ? passwordEncoder.encode(citizen.getPassword()) : userEntity.getPassword());
    userEntity = userRepository.save(userEntity);

    citizenEntity.setUser(userEntity);
    citizenEntity.setName(citizen.getName() != null ? citizen.getName() : citizenEntity.getName());
    citizenEntity.setPhoneNumber(citizen.getPhoneNumber() != null ? citizen.getPhoneNumber() : citizenEntity.getPhoneNumber());
    citizenEntity.setCoin(citizen.getCoin() != null ? citizen.getCoin() : citizenEntity.getCoin());
    citizenEntity.setRating(citizen.getRating() != null ? citizen.getRating() : citizenEntity.getRating());
    citizenEntity.setUpdatedAt(LocalDateTime.now());

    if (imageFile != null && !imageFile.isEmpty()) {
      try {
        String filePath = fileUploadHelper.uploadFile("citizens", citizen.getEmail(), imageFile, citizenEntity.getImageUrl());
        String imageUrl = fileUploadHelper.generateFileUrl(filePath);
        citizenEntity.setImageUrl(imageUrl);
      } catch (Exception e) {
        throw new RuntimeException(e.getMessage());
      }
    }

    return CitizenTransformer.transformEntityToModel(
        citizenRepository.save(citizenEntity)
    );
  }

  @Transactional
  public void delete(String citizenId) {
    if (!citizenRepository.existsById(UUID.fromString(citizenId))) {
      throw new EntityNotFoundException("Citizen with ID " + citizenId + " Not Found");
    }

    citizenRepository.deleteById(UUID.fromString(citizenId));
  }

  @Transactional
  public void addCoin(String userId, BigInteger totalCoin) {
    CitizenEntity citizenEntity = citizenRepository.findById(UUID.fromString(userId))
        .orElseThrow(() -> new EntityNotFoundException("Citizen with ID " + userId + " not found"));
    citizenEntity.setCoin(citizenEntity.getCoin().add(totalCoin));
    citizenRepository.save(citizenEntity);
  }

  @Transactional
  public void addRating(String userId, BigInteger totalRating) {
    CitizenEntity citizenEntity = citizenRepository.findById(UUID.fromString(userId))
        .orElseThrow(() -> new EntityNotFoundException("Citizen with ID " + userId + " not found"));
    citizenEntity.setRating(citizenEntity.getCoin().add(totalRating));
    citizenRepository.save(citizenEntity);
  }

  public Citizen findCitizenByPhoneNumber(String phoneNumber) {
    Optional<CitizenEntity> citizenEntityOptional = citizenRepository.findByPhoneNumber(phoneNumber);

    if (citizenEntityOptional.isEmpty()) {
      throw new BadRequestException("User Not Found");
    }

    return CitizenTransformer.transformEntityToModel(
        citizenEntityOptional.get()
    );
  }

  public Citizen findCitizenByBleId(BigInteger bleId) {
    Optional<CitizenEntity> citizenEntityOptional = citizenRepository.findByBleId(bleId);

    if (citizenEntityOptional.isEmpty()) {
      throw new BadRequestException("User Not Found");
    }

    return CitizenTransformer.transformEntityToModel(
        citizenEntityOptional.get()
    );
  }

  public boolean isPhoneNumberAlreadyExists(String phoneNumber) {
    Optional<CitizenEntity> citizenEntityOptional = citizenRepository.findByPhoneNumber(phoneNumber);

    if (citizenEntityOptional.isPresent()) {
      return true;
    }

    return false;
  }

  public boolean isCitizenExists(String citizenId) {
    return citizenRepository.existsById(UUID.fromString(citizenId));
  }
}
