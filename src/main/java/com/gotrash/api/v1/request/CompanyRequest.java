package com.gotrash.api.v1.request;

import com.gotrash.constant.UserRole;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CompanyRequest {
  private String email;
  private String password;
  private UserRole role;
  private String name;
  private String address;
}
