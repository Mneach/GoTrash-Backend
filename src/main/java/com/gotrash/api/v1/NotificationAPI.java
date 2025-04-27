package com.gotrash.api.v1;

import com.gotrash.api.response.ApiResponse;
import com.gotrash.api.response.MessageResponse;
import com.gotrash.api.v1.model.Notification;
import com.gotrash.api.v1.request.NotificationRequest;
import com.gotrash.api.v1.response.NotificationResponse;
import com.gotrash.api.v1.transformer.NotificationTransformer;
import com.gotrash.service.NotificationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
@Tag(name = "Notification API", description = "API for notification")
public class NotificationAPI {
    private final NotificationService notificationService;

    @PostMapping("/notifications")
    @Operation(summary = "API to create a new notification")
    public ApiResponse<NotificationResponse> save(@RequestBody NotificationRequest notificationRequest) {
        Notification notification = NotificationTransformer.transformRequestToModel(notificationRequest);
        NotificationResponse notificationResponse = NotificationTransformer.transformModelToResponse(notificationService.save(notification));
        return new ApiResponse<>(HttpStatus.CREATED.value(), notificationResponse);
    }

    @GetMapping("/notifications")
    @Operation(summary = "API to get all notification data")
    public ApiResponse<List<NotificationResponse>> getNotifications() {
        List<Notification> notifications = notificationService.getNotifications();
        List<NotificationResponse> notificationResponses = notifications.stream()
            .map(NotificationTransformer::transformModelToResponse)
            .toList();
        return new ApiResponse<>(HttpStatus.OK.value(), notificationResponses);
    }


    @GetMapping("/notifications/{notification_id}")
    @Operation(summary = "API to get notification by notification id")
    public ApiResponse<NotificationResponse> getUserByNotificationId(@PathVariable("notification_id") String notificationId) {
        NotificationResponse notificationResponse = NotificationTransformer.transformModelToResponse(
                notificationService.getNotificationByNotificationId(notificationId)
        );
        return new ApiResponse<>(HttpStatus.OK.value(), notificationResponse);
    }

    @GetMapping("/notifications/user/{user_id}")
    @Operation(summary = "API to get notifications by user id")
    public ApiResponse<List<NotificationResponse>> getUserByUserId(@PathVariable("user_id") String userId) {
        List<Notification> notifications = notificationService.getNotificationByUserId(userId);
        List<NotificationResponse> notificationResponse = notifications
                .stream()
                .map(NotificationTransformer::transformModelToResponse)
                .toList();
        return new ApiResponse<>(HttpStatus.OK.value(), notificationResponse);
    }

    @PatchMapping("/notifications/{notification_id}")
    @Operation(summary = "API to update notification by notification id")
    public ApiResponse<NotificationResponse> update(@PathVariable("notification_id") String notificationId,
                                                       @RequestBody NotificationRequest notificationRequest) {
        Notification notification = NotificationTransformer.transformRequestToModel(notificationId, notificationRequest);
        NotificationResponse notificationResponse = NotificationTransformer.transformModelToResponse(notificationService.update(notification));
        return new ApiResponse<>(HttpStatus.CREATED.value(), notificationResponse);
    }

    @DeleteMapping("/notifications/{notification_id}")
    @Operation(summary = "API to delete notification by notification id")
    public ApiResponse<MessageResponse> delete(@PathVariable("notification_id") String notificationId) {
        notificationService.delete(notificationId);
        String message = "Successfully delete notification with id " + notificationId;
        return new ApiResponse<>(HttpStatus.OK.value(), message);
    }
}
