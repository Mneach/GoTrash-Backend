package com.gotrash.api.v1.transformer;

import com.gotrash.api.v1.model.Auth;
import com.gotrash.api.v1.request.AuthRequest;

public class AuthTransformer {

  public static Auth transformRequestToModel(AuthRequest authRequest) {
    return Auth.builder()
        .email(authRequest.getEmail())
        .password(authRequest.getPassword())
        .build();
  }
}
