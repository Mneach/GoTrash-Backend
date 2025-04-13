package com.gotrash.security;

import com.gotrash.api.v1.model.User;
import com.gotrash.service.JwtService;
import com.gotrash.service.UserService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

import static com.gotrash.util.AuthorityUtil.mapRolesToAuthorities;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

  private final JwtService jwtService;
  private final UserService userService;

  @Override
  protected void doFilterInternal(HttpServletRequest request,
                                  HttpServletResponse response,
                                  FilterChain filterChain) throws ServletException, IOException {

    String authHeader = request.getHeader("Authorization");
    String jwt;
    String userId;

    if (authHeader == null || !authHeader.startsWith("Bearer ")) {
      filterChain.doFilter(request, response);
      return;
    }

    jwt = authHeader.substring(7);
    userId = jwtService.extractUserId(jwt);

    if (userId != null && SecurityContextHolder.getContext().getAuthentication() == null) {
      User user = userService.getUserByUserId(userId);

      if (jwtService.isTokenValid(jwt, user)) {
        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
            userId,
            user.getPassword(),
            mapRolesToAuthorities(List.of(user.getRole()))
        );
        SecurityContextHolder.getContext().setAuthentication(authToken);
      }
    }

    filterChain.doFilter(request, response);
  }
}
