package com.gotrash.api.v1.transformer;

import com.gotrash.api.v1.model.Role;
import com.gotrash.api.v1.request.RoleRequest;
import com.gotrash.api.v1.response.RoleResponse;
import com.gotrash.entity.RoleEntity;

import java.util.UUID;

public class RoleTransformer {

  public static Role transformRequestToModel(RoleRequest roleRequest) {
    return Role.builder()
        .roleId(roleRequest.getRoleId())
        .name(roleRequest.getRoleName())
        .build();
  }

  public static Role transformEntityToModel(RoleEntity roleEntity) {
    return Role.builder()
        .roleId(roleEntity.getRoleId().toString())
        .name(roleEntity.getName())
        .createdAt(roleEntity.getCreatedAt())
        .updatedAt(roleEntity.getUpdatedAt())
        .build();
  }

  public static RoleEntity transformModelToEntity(Role role) {
    return RoleEntity.builder()
        .roleId(role.getRoleId() != null ? UUID.fromString(role.getRoleId()) : null)
        .name(role.getName())
        .createdAt(role.getCreatedAt())
        .updatedAt(role.getUpdatedAt())
        .build();
  }

  public static RoleResponse transformModelToResponse(Role role) {
    return RoleResponse.builder()
        .roleId(role.getRoleId())
        .name(role.getName())
        .createdAt(role.getCreatedAt())
        .updatedAt(role.getUpdatedAt())
        .build();
  }
}
