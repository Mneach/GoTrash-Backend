package com.gotrash.api.v1.transformer;

import com.gotrash.api.v1.model.Notification;
import com.gotrash.api.v1.model.User;
import com.gotrash.api.v1.request.NotificationRequest;
import com.gotrash.api.v1.response.NotificationResponse;
import com.gotrash.entity.NotificationEntity;

import java.time.LocalDateTime;
import java.util.UUID;

public class NotificationTransformer {

    public static Notification transformRequestToModel(NotificationRequest notificationRequest) {
        return Notification.builder()
                .notificationId(notificationRequest.getNotificationId())
                .user(User.builder().userId(notificationRequest.getUserId()).build())
                .title(notificationRequest.getTitle())
                .description(notificationRequest.getDescription())
                .build();
    }

    public static NotificationEntity transformModelToEntity(Notification notification) {
        return NotificationEntity.builder()
                .notificationId(notification.getNotificationId() != null ? UUID.fromString(notification.getNotificationId()) : null)
                .user(UserTransformer.transformModelToEntity(notification.getUser()))
                .title(notification.getTitle())
                .description(notification.getDescription())
                .build();
    }

    public static Notification transformEntityToModel(NotificationEntity notificationEntity) {
        return Notification.builder()
                .notificationId(notificationEntity.getNotificationId().toString())
                .user(UserTransformer.transformEntityToModel(notificationEntity.getUser()))
                .title(notificationEntity.getTitle())
                .description(notificationEntity.getDescription())
                .createdAt(notificationEntity.getCreatedAt())
                .updatedAt(notificationEntity.getUpdatedAt())
                .build();
    }

    public static NotificationResponse transformModelToResponse(Notification notification) {
        return NotificationResponse.builder()
                .notificationId(notification.getNotificationId())
                .user(UserTransformer.transformModelToResponse(notification.getUser()))
                .title(notification.getTitle())
                .createdAt(notification.getCreatedAt())
                .updatedAt(notification.getUpdatedAt())
                .description(notification.getDescription())
                .build();
    }
}
