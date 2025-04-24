package com.gotrash.service;


import com.gotrash.api.v1.model.Government;
import com.gotrash.api.v1.model.User;
import com.gotrash.api.v1.transformer.GovernmentTransformer;
import com.gotrash.api.v1.transformer.UserTransformer;
import com.gotrash.entity.GovernmentEntity;
import com.gotrash.entity.UserEntity;
import com.gotrash.repository.GovernmentRepository;
import com.gotrash.repository.UserRepository;
import com.gotrash.util.AuthorityUtil;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class GovernmentService {

  private final GovernmentRepository governmentRepository;
  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;

  @Transactional
  public Government save(Government government) {

    User user = User.builder()
        .email(government.getEmail())
        .role(government.getRole())
        .password(passwordEncoder.encode(government.getPassword()))
        .build();

    UserEntity userEntity = userRepository.save(UserTransformer.transformModelToEntity(user));

    GovernmentEntity governmentEntity = GovernmentTransformer.transformModelToEntity(government);
    governmentEntity.setUser(userEntity);

    return GovernmentTransformer.transformEntityToModel(
        governmentRepository.save(governmentEntity)
    );
  }

  public List<Government> getGovernments() {
    List<GovernmentEntity> governmentEntities = governmentRepository.findAll();

    return governmentEntities.stream()
        .map(GovernmentTransformer::transformEntityToModel)
        .toList();
  }

  public Government getGovernmentByUserId(String userId) {
    Optional<GovernmentEntity> trashBinEntityOptional = governmentRepository.findByUser_UserId(UUID.fromString(userId));

    if (trashBinEntityOptional.isEmpty()) {
      throw new EntityNotFoundException("Government with User ID " + userId + " Not Found");
    }

    return GovernmentTransformer.transformEntityToModel(trashBinEntityOptional.get());
  }

  public Government getMe() {
    String userId = AuthorityUtil.getCurrentUserId();
    return getGovernmentByUserId(userId);
  }

  @Transactional
  public Government update(Government government) {

    GovernmentEntity governmentEntity = governmentRepository.findById(UUID.fromString(government.getUserId()))
        .orElseThrow(() -> new EntityNotFoundException("Government with ID " + government.getUserId() + " not found"));

    UserEntity userEntity = userRepository.findById(UUID.fromString(government.getUserId()))
        .orElseThrow(() -> new EntityNotFoundException("User with ID " + government.getUserId() + " not found"));

    userEntity.setEmail(government.getEmail());
    userEntity.setRole(government.getRole());
    userEntity.setPassword(passwordEncoder.encode(government.getPassword()));
    userEntity = userRepository.save(userEntity);

    governmentEntity.setUser(userEntity);
    governmentEntity.setName(government.getName());
    governmentEntity.setUpdatedAt(LocalDateTime.now());

    return GovernmentTransformer.transformEntityToModel(
        governmentRepository.save(governmentEntity)
    );
  }

  @Transactional
  public void delete(String governmentId) {
    if (!governmentRepository.existsById(UUID.fromString(governmentId))) {
      throw new EntityNotFoundException("Government with ID " + governmentId + " Not Found");
    }

    governmentRepository.deleteById(UUID.fromString(governmentId));
  }
}
