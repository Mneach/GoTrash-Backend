package com.gotrash.api.v1.transformer;

import com.gotrash.api.v1.model.User;
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
                .createdAt(user.getCreatedAt())
                .updatedAt(user.getUpdatedAt())
                .build();
    }


    public static User transformEntityToModel(UserEntity userEntity) {
        return User.builder()
                .userId(userEntity.getUserId().toString())
                .username(userEntity.getUsername())
                .email(userEntity.getEmail())
                .phoneNumber(userEntity.getPhoneNumber())
                .imageUrl(userEntity.getImageUrl())
                .coin(userEntity.getCoin())
                .createdAt(userEntity.getCreatedAt())
                .updatedAt(userEntity.getUpdatedAt())
                .build();
    }
}
