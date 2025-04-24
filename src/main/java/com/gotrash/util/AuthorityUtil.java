package com.gotrash.util;

import com.gotrash.constant.UserRole;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class AuthorityUtil {

  public static String getCurrentUserId() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    if (authentication == null || !authentication.isAuthenticated()) return null;

    return authentication.getName();
  }

  public static Collection<GrantedAuthority> mapRolesToAuthorities(List<UserRole> roles) {
    return roles.stream()
        .map(role -> new SimpleGrantedAuthority(role.toString()))
        .collect(Collectors.toList());
  }
}
