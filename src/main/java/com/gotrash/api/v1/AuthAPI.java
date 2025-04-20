package com.gotrash.api.v1;

import com.gotrash.api.v1.model.Auth;
import com.gotrash.api.v1.model.User;
import com.gotrash.api.v1.request.AuthRequest;
import com.gotrash.api.v1.request.UserRequest;
import com.gotrash.api.v1.response.AuthResponse;
import com.gotrash.api.v1.transformer.AuthTransformer;
import com.gotrash.api.v1.transformer.UserTransformer;
import com.gotrash.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("v1")
@RequiredArgsConstructor
@Tag(name = "Auth", description = "API for authentication and authorization")
public class AuthAPI {

  private final AuthService authService;

  @PostMapping("auth/register")
  @Operation(summary = "API for register a new user")
  public ResponseEntity<AuthResponse> register(@RequestBody UserRequest userRequest) {
    User user = UserTransformer.transformRequestToModel(userRequest);
    AuthResponse authResponse = authService.register(user);

    return new ResponseEntity<>(authResponse, HttpStatus.OK);
  }

  @PostMapping("auth/login")
  @Operation(summary = "API for login to the application")
  public ResponseEntity<AuthResponse> auth(@RequestBody AuthRequest authRequest) {
    Auth auth = AuthTransformer.transformRequestToModel(authRequest);
    AuthResponse authResponse = authService.login(auth);

    return new ResponseEntity<>(authResponse, HttpStatus.OK);
  }
}
