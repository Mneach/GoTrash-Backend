package com.gotrash.repository;

import com.gotrash.constant.RoleName;
import com.gotrash.entity.RoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface RoleRepository extends JpaRepository<RoleEntity, UUID> {
  Optional<RoleEntity> findByName(RoleName name);
}
