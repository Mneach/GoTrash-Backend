package com.gotrash.api.v1.transformer;

import com.gotrash.api.v1.model.Role;
import com.gotrash.api.v1.model.User;
import com.gotrash.api.v1.request.UserRequest;
import com.gotrash.api.v1.response.UserResponse;
import com.gotrash.entity.UserEntity;

import java.util.UUID;

public class UserTransformer {

    public static UserEntity transformModelToEntity(User user) {
        return UserEntity.builder()
                .userId(user.getUserId() != null ? UUID.fromString(user.getUserId()) : null)
                .username(user.getUsername())
                .password(user.getPassword())
                .email(user.getEmail())
                .phoneNumber(user.getPhoneNumber())
                .imageUrl(user.getImageUrl())
                .coin(user.getCoin())
                .role(RoleTransformer.transformModelToEntity(user.getRole()))
                .createdAt(user.getCreatedAt())
                .updatedAt(user.getUpdatedAt())
                .build();
    }

    public static User transformEntityToModel(UserEntity userEntity) {
        return User.builder()
                .userId(userEntity.getUserId().toString())
                .username(userEntity.getUsername())
                .password(userEntity.getPassword())
                .email(userEntity.getEmail())
                .phoneNumber(userEntity.getPhoneNumber())
                .imageUrl(userEntity.getImageUrl())
                .coin(userEntity.getCoin())
                .role(RoleTransformer.transformEntityToModel(userEntity.getRole()))
                .createdAt(userEntity.getCreatedAt())
                .updatedAt(userEntity.getUpdatedAt())
                .build();
    }

    public static User transformRequestToModel(UserRequest userRequest) {
        return User.builder()
            .userId(userRequest.getUserId())
            .username(userRequest.getUsername())
            .password(userRequest.getPassword())
            .email(userRequest.getEmail())
            .phoneNumber(userRequest.getPhoneNumber())
            .imageUrl(userRequest.getImageUrl())
            .coin(userRequest.getCoin())
            .role(Role.builder().name(userRequest.getRole()).build())
            .build();
    }

    public static UserResponse transformModelToResponse(User user) {
        return UserResponse.builder()
            .userId(user.getUserId())
            .username(user.getUsername())
            .email(user.getEmail())
            .phoneNumber(user.getPhoneNumber())
            .imageUrl(user.getImageUrl())
            .coin(user.getCoin())
            .role(user.getRole().getName())
            .createdAt(user.getCreatedAt())
            .updatedAt(user.getUpdatedAt())
            .build();
    }
}
