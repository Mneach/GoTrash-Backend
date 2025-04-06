package com.gotrash.api.v1.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TrashBinResponse {
  private String trashBinId;
  private Double latitude;
  private Double longitude;
  private String address;
  private String imageName;
  private String imageUrl;
  private LocalDateTime createdAt;
  private LocalDateTime updatedAt;
}
