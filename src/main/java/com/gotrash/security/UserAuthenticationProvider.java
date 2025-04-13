package com.gotrash.security;

import com.gotrash.api.v1.model.User;
import com.gotrash.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

import static com.gotrash.util.AuthorityUtil.mapRolesToAuthorities;

/**
 * Custom AuthenticationProvider that uses UserService to authenticate a user
 * based on email and password. This class is responsible for validating
 * the login credentials (email and password) and returning an authenticated
 * UsernamePasswordAuthenticationToken if successful.
 *
 * It is mainly used during the login process, and is triggered when
 * AuthenticationManager is called from AuthService.
 *
 * The `supports()` method tells Spring Security that this provider supports
 * UsernamePasswordAuthenticationToken, which is the standard token used
 * for authentication with a username and password (like in JSON login requests).
 *
 * Example Usage Flow:
 * - User sends email/password to /login
 * - AuthService calls `authenticationManager.authenticate(...)`
 * - Spring Security uses this provider to validate credentials
 * - If successful, the userId is stored in the SecurityContext
 */
@RequiredArgsConstructor
public class UserAuthenticationProvider implements AuthenticationProvider {

  private final UserService userService;
  private final PasswordEncoder passwordEncoder;

  @Override
  public Authentication authenticate(Authentication authentication) throws AuthenticationException {

    String email = authentication.getName();
    String password = authentication.getCredentials().toString();

    User user = userService.getUserByEmail(email);

    if (user == null || !passwordEncoder.matches(password, user.getPassword())) {
      throw new BadCredentialsException("Invalid email or password");
    }

    return new UsernamePasswordAuthenticationToken(
        user.getUserId(),
        user.getPassword(),
        mapRolesToAuthorities(List.of(user.getRole()))
    );
  }

  /**
   * Specifies that this AuthenticationProvider supports UsernamePasswordAuthenticationToken,
   * which is used during authentication with username (or email) and password.
   *
   * This ensures Spring Security can use this provider when `authenticationManager.authenticate(...)`
   * is called with UsernamePasswordAuthenticationToken.
   */
  @Override
  public boolean supports(Class<?> authentication) {
    return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
  }
}
