package com.gotrash.api.v1.request;

import com.gotrash.api.v1.model.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class NotificationRequest {
    private String userId;
    private String title;
    private String description;
}
