package com.gotrash.service;

import com.gotrash.api.v1.model.Auth;
import com.gotrash.api.v1.model.User;
import com.gotrash.api.v1.response.AuthResponse;
import com.gotrash.api.v1.transformer.UserTransformer;
import com.gotrash.entity.UserEntity;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AuthService {

  private final UserService userService;
  private final JwtService jwtService;
  private final AuthenticationManager authenticationManager;
  private final PasswordEncoder passwordEncoder;

  public AuthResponse register(User user) {
    user.setPassword(passwordEncoder.encode(user.getPassword()));
    user = userService.save(user);
    String jwtToken = jwtService.generateToken(user);

    return AuthResponse.builder()
        .token(jwtToken)
        .build();
  }

  public AuthResponse login(Auth auth) {
      authenticationManager.authenticate(
          new UsernamePasswordAuthenticationToken(
              auth.getEmail(),
              auth.getPassword()
          )
      );

    User user = userService.getUserByEmail(auth.getEmail());
    String jwtToken = jwtService.generateToken(user);

    return AuthResponse.builder()
        .token(jwtToken)
        .build();
  }

}
