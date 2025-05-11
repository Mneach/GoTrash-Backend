package com.gotrash.api.v1.request;

import com.gotrash.constant.UserRole;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigInteger;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CitizenRequest {
  private String email;
  private String password;
  private UserRole role;
  private String name;
  private String phoneNumber;
  private MultipartFile imageFile;
  private BigInteger coin;
  private BigInteger rating;
  private Integer currentStreak;
  private Integer longestStreak;
  private LocalDate lastTrashDate;
}
