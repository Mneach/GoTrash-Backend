package com.gotrash.api.v1.request;

import com.gotrash.constant.UserRole;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class WasteBankRequest {
  private String email;
  private UserRole role;
  private String name;
  private String password;
  private Double latitude;
  private Double longitude;
  private MultipartFile imageFile;
  private String address;
  private String phoneNumber;
  private LocalDateTime createdAt;
  private LocalDateTime updatedAt;
}
