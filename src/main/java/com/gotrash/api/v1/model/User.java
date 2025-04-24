package com.gotrash.api.v1.model;

import com.gotrash.constant.UserRole;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class User{
    private String userId;
    private String email;
    private String password;
    private UserRole role;
    private Long version;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
