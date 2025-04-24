package com.gotrash.api.v1.request;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TrashBinRequest {
  private String name;
  private String wasteBankId;
  private Double latitude;
  private Double longitude;
  private String address;
  private String imageName;
  private String imageUrl;
}
