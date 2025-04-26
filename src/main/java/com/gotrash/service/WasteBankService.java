package com.gotrash.service;

import com.gotrash.api.v1.model.User;
import com.gotrash.api.v1.model.WasteBank;
import com.gotrash.api.v1.transformer.UserTransformer;
import com.gotrash.api.v1.transformer.WasteBankTransformer;
import com.gotrash.entity.UserEntity;
import com.gotrash.entity.WasteBankEntity;
import com.gotrash.repository.UserRepository;
import com.gotrash.repository.WasteBankRepository;
import com.gotrash.util.AuthorityUtil;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

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
  public WasteBank update(WasteBank wasteBank) {

    WasteBankEntity wasteBankEntity = wasteBankRepository.findById(UUID.fromString(wasteBank.getUserId()))
        .orElseThrow(() -> new EntityNotFoundException("Waste Bank with ID " + wasteBank.getUserId() + " not found"));

    UserEntity userEntity = userRepository.findById(UUID.fromString(wasteBank.getUserId()))
        .orElseThrow(() -> new EntityNotFoundException("User with ID " + wasteBank.getUserId() + " not found"));

    userEntity.setEmail(wasteBank.getEmail());
    userEntity.setRole(wasteBank.getRole());
    userEntity.setPassword(passwordEncoder.encode(wasteBank.getPassword()));

    userEntity = userRepository.save(userEntity);

    wasteBankEntity.setUser(userEntity);
    wasteBankEntity.setName(wasteBank.getName());
    wasteBankEntity.setAddress(wasteBank.getAddress());
    wasteBankEntity.setLatitude(wasteBank.getLatitude());
    wasteBankEntity.setLongitude(wasteBank.getLongitude());
    wasteBankEntity.setImageUrl(wasteBank.getImageUrl());
    wasteBankEntity.setImageName(wasteBank.getImageName());
    wasteBankEntity.setUpdatedAt(LocalDateTime.now());

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
  
}
