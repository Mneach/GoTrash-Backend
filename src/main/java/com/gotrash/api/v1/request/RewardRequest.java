package com.gotrash.api.v1.request;

import com.gotrash.api.v1.model.RewardCategory;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigInteger;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RewardRequest {
    private String rewardCategoryId;
    private String name;
    private BigInteger coin;
    private Integer stock;
    private MultipartFile imageFile;
    private String description;
}
