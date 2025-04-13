package com.gotrash.util;

import com.gotrash.api.v1.model.Role;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class AuthorityUtil {

  public static Collection<GrantedAuthority> mapRolesToAuthorities(List<Role> roles) {
    return roles.stream()
        .map(role -> new SimpleGrantedAuthority(role.getName().toString()))
        .collect(Collectors.toList());
  }
}
