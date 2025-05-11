package com.gotrash.api.v1.response;

import com.gotrash.api.v1.model.WasteBankWarehouse;
import com.gotrash.constant.UserRole;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class WasteBankResponse {
  private String userId;
  private String email;
  private UserRole role;
  private String name;
  private Double latitude;
  private Double longitude;
  private String address;
  private String imageUrl;
  private LocalDateTime createdAt;
  private LocalDateTime updatedAt;
  private List<WasteBankWarehouseResponse> wasteBankWarehouses;
}
