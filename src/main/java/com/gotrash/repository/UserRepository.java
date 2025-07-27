package com.gotrash.repository;

import com.gotrash.constant.UserRole;
import com.gotrash.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, UUID> {
  Optional<UserEntity> findByEmail(String email);

  List<UserEntity> findAllByRole(UserRole role);

  Optional<UserEntity> findByUserIdAndRole(UUID userId, UserRole role);
}
