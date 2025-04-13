package com.gotrash.service;

import com.gotrash.api.v1.model.Role;
import com.gotrash.api.v1.transformer.RoleTransformer;
import com.gotrash.constant.RoleName;
import com.gotrash.repository.RoleRepository;
import com.gotrash.entity.RoleEntity;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RoleService {

  private final RoleRepository roleRepository;
  
  public Role save(Role role) {
    RoleEntity roleEntity = RoleTransformer.transformModelToEntity(role);
    return RoleTransformer.transformEntityToModel(
        roleRepository.save(roleEntity)
    );
  }

  public Role getRoleByRoleId(String roleId) {

    Optional<RoleEntity> roleEntityOptional = roleRepository.findById(UUID.fromString(roleId));

    if (roleEntityOptional.isEmpty()) {
      throw new EntityNotFoundException("Role with ID " + roleId + " Not Found");
    }

    return RoleTransformer.transformEntityToModel(roleEntityOptional.get());
  }

  public Role getRoleByRoleName(RoleName roleName) {
    Optional<RoleEntity> roleEntityOptional = roleRepository.findByName(roleName);

    if (roleEntityOptional.isEmpty()) {
      throw new EntityNotFoundException("Role with ID " + roleName + " Not Found");
    }

    return RoleTransformer.transformEntityToModel(roleEntityOptional.get());
  }

  public Role update(Role role) {

    if (!roleRepository.existsById(UUID.fromString(role.getRoleId()))) {
      throw new EntityNotFoundException("Role with ID " + role.getRoleId() + " Not Found");
    }

    RoleEntity roleEntity = RoleTransformer.transformModelToEntity(role);
    return RoleTransformer.transformEntityToModel(
        roleRepository.save(roleEntity)
    );
  }

  public void delete(String roleId) {
    if (!roleRepository.existsById(UUID.fromString(roleId))) {
      throw new EntityNotFoundException("Role with ID " + roleId + " Not Found");
    }

    roleRepository.deleteById(UUID.fromString(roleId));
  }
}
