package com.gotrash.service;

import com.gotrash.api.v1.model.User;
import com.gotrash.api.v1.transformer.UserTransformer;
import com.gotrash.entity.UserEntity;
import com.gotrash.exception.rest.BadRequestException;
import com.gotrash.repository.UserRepository;
import com.gotrash.util.AuthorityUtil;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
public class AdminService {

  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;

  @Transactional
  public User save(User user) {

    user = User.builder()
        .email(user.getEmail())
        .role(user.getRole())
        .password(passwordEncoder.encode(user.getPassword()))
        .build();

    UserEntity userEntity = userRepository.save(UserTransformer.transformModelToEntity(user));

    return UserTransformer.transformEntityToModel(userEntity);
  }

  public List<User> getAdmins() {
    List<UserEntity> UserEntities = userRepository.findAll();

    return UserEntities.stream()
        .map(UserTransformer::transformEntityToModel)
        .toList();
  }


  public User getAdminByUserId(String userId) {
    Optional<UserEntity> userEntityOptional = userRepository.findById(UUID.fromString(userId));

    if (userEntityOptional.isEmpty()) {
      throw new EntityNotFoundException("Admin with User ID " + userId + " Not Found");
    }

    return UserTransformer.transformEntityToModel(userEntityOptional.get());
  }

  public User getMe() {
    String userId = AuthorityUtil.getCurrentUserId();
    return getAdminByUserId(userId);
  }

  @Transactional
  public User update(User User) {

    UserEntity userEntity = userRepository.findById(UUID.fromString(User.getUserId()))
        .orElseThrow(() -> new EntityNotFoundException("User with ID " + User.getUserId() + " not found"));

    if (User.getEmail() != null && !User.getEmail().equals(userEntity.getEmail())) {
      if (userRepository.findByEmail(User.getEmail()).isPresent()) {
        throw new BadRequestException("Email is already in use.");
      }
    }

    userEntity.setEmail(User.getEmail() != null ? User.getEmail() : userEntity.getEmail());
    userEntity.setRole(User.getRole() != null ? User.getRole() : userEntity.getRole());
    userEntity.setPassword(User.getPassword() != null ? passwordEncoder.encode(User.getPassword()) : userEntity.getPassword());

    userEntity = userRepository.save(userEntity);

    return UserTransformer.transformEntityToModel(userEntity);
  }

  @Transactional
  public void delete(String UserId) {
    if (!userRepository.existsById(UUID.fromString(UserId))) {
      throw new EntityNotFoundException("Waste Bank with ID " + UserId + " Not Found");
    }

    userRepository.deleteById(UUID.fromString(UserId));
  }
}
