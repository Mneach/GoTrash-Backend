package com.gotrash.api.v1.transformer;

import com.gotrash.api.v1.model.User;
import com.gotrash.api.v1.request.UserRequest;
import com.gotrash.api.v1.response.UserResponse;
import com.gotrash.entity.UserEntity;

import java.util.UUID;

public class UserTransformer {

    public static UserEntity transformModelToEntity(User user) {
        return UserEntity.builder()
                .userId(user.getUserId() != null ? UUID.fromString(user.getUserId()) : null)
                .password(user.getPassword())
                .email(user.getEmail())
                .role(user.getRole())
                .createdAt(user.getCreatedAt())
                .updatedAt(user.getUpdatedAt())
                .version(user.getVersion())
                .build();
    }

    public static User transformEntityToModel(UserEntity userEntity) {
        return User.builder()
                .userId(userEntity.getUserId().toString())
                .password(userEntity.getPassword())
                .email(userEntity.getEmail())
                .role(userEntity.getRole())
                .createdAt(userEntity.getCreatedAt())
                .updatedAt(userEntity.getUpdatedAt())
                .version(userEntity.getVersion())
                .build();
    }

    public static User transformRequestToModel(String userId, UserRequest userRequest) {
        return User.builder()
            .userId(userId)
            .password(userRequest.getPassword())
            .email(userRequest.getEmail())
            .role(userRequest.getRole())
            .build();
    }

    public static User transformRequestToModel(UserRequest userRequest) {
        return User.builder()
            .password(userRequest.getPassword())
            .email(userRequest.getEmail())
            .role(userRequest.getRole())
            .build();
    }

    public static UserResponse transformModelToResponse(User user) {
        return UserResponse.builder()
            .userId(user.getUserId())
            .email(user.getEmail())
            .role(user.getRole())
            .createdAt(user.getCreatedAt())
            .updatedAt(user.getUpdatedAt())
            .build();
    }
}
