package com.gotrash.api.v1.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigInteger;
import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class User {
    private String userId;
    private String username;
    private String password;
    private String email;
    private String phoneNumber;
    private String imageUrl;
    private BigInteger coin;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
