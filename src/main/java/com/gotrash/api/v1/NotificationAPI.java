package com.gotrash.api.v1;

import com.gotrash.api.response.MessageResponse;
import com.gotrash.api.v1.model.Group;
import com.gotrash.api.v1.model.Notification;
import com.gotrash.api.v1.request.NotificationRequest;
import com.gotrash.api.v1.response.GroupResponse;
import com.gotrash.api.v1.response.NotificationResponse;
import com.gotrash.api.v1.transformer.GroupTransformer;
import com.gotrash.api.v1.transformer.NotificationTransformer;
import com.gotrash.service.NotificationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<NotificationResponse> save(@RequestBody NotificationRequest notificationRequest) {
        Notification notification = NotificationTransformer.transformRequestToModel(notificationRequest);
        NotificationResponse notificationResponse = NotificationTransformer.transformModelToResponse(notificationService.save(notification));
        return new ResponseEntity<>(notificationResponse, HttpStatus.CREATED);
    }

    @GetMapping("/notifications")
    @Operation(summary = "API to get all notification data")
    public ResponseEntity<List<NotificationResponse>> getNotifications() {
        List<Notification> notifications = notificationService.getNotifications();
        List<NotificationResponse> notificationResponses = notifications.stream()
            .map(NotificationTransformer::transformModelToResponse)
            .toList();
        return new ResponseEntity<>(notificationResponses, HttpStatus.OK);
    }


    @GetMapping("/notifications/{notification_id}")
    @Operation(summary = "API to get notification by notification id")
    public ResponseEntity<NotificationResponse> getUserByNotificationId(@PathVariable("notification_id") String notificationId) {
        NotificationResponse notificationResponse = NotificationTransformer.transformModelToResponse(
                notificationService.getNotificationByNotificationId(notificationId)
        );
        return new ResponseEntity<>(notificationResponse, HttpStatus.OK);
    }

    @GetMapping("/notifications/user/{user_id}")
    @Operation(summary = "API to get notifications by user id")
    public ResponseEntity<List<NotificationResponse>> getUserByUserId(@PathVariable("user_id") String userId) {
        List<Notification> notifications = notificationService.getNotificationByUserId(userId);
        List<NotificationResponse> notificationResponse = notifications
                .stream()
                .map(NotificationTransformer::transformModelToResponse)
                .toList();
        return new ResponseEntity<>(notificationResponse, HttpStatus.OK);
    }

    @PatchMapping("/notifications/{notification_id}")
    @Operation(summary = "API to update notification by notification id")
    public ResponseEntity<NotificationResponse> update(@PathVariable("notification_id") String notificationId,
                                                       @RequestBody NotificationRequest notificationRequest) {
        Notification notification = NotificationTransformer.transformRequestToModel(notificationId, notificationRequest);
        NotificationResponse notificationResponse = NotificationTransformer.transformModelToResponse(notificationService.update(notification));
        return new ResponseEntity<>(notificationResponse, HttpStatus.CREATED);
    }

    @DeleteMapping("/notifications/{notification_id}")
    @Operation(summary = "API to delete notification by notification id")
    public ResponseEntity<MessageResponse> delete(@PathVariable("notification_id") String notificationId) {
        notificationService.delete(notificationId);
        String message = "Successfully delete notification with id " + notificationId;
        return new ResponseEntity(message, HttpStatus.OK);
    }
}
