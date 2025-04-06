package com.gotrash.api.v1;

import com.gotrash.api.response.MessageResponse;
import com.gotrash.api.v1.model.Notification;
import com.gotrash.api.v1.request.NotificationRequest;
import com.gotrash.api.v1.response.NotificationResponse;
import com.gotrash.api.v1.transformer.NotificationTransformer;
import com.gotrash.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("v1")
public class NotificationAPI {
    private final NotificationService notificationService;

    @PostMapping("/notification")
    public ResponseEntity<NotificationResponse> save(@RequestBody NotificationRequest notificationRequest) {
        Notification notification = NotificationTransformer.transformRequestToModel(notificationRequest);
        NotificationResponse notificationResponse = NotificationTransformer.transformModelToResponse(notificationService.save(notification));
        return new ResponseEntity<>(notificationResponse, HttpStatus.CREATED);
    }

    @GetMapping("/notification/{notification_id}")
    public ResponseEntity<NotificationResponse> getUserByNotificationId(@PathVariable("notification_id") String notificationId) {
        NotificationResponse notificationResponse = NotificationTransformer.transformModelToResponse(
                notificationService.getNotificationByNotificationId(notificationId)
        );
        return new ResponseEntity<>(notificationResponse, HttpStatus.OK);
    }

    @GetMapping("/notification/user/{user_id}")
    public ResponseEntity<List<NotificationResponse>> getUserByUserId(@PathVariable("user_id") String userId) {
        List<Notification> notifications = notificationService.getNotificationByUserId(userId);
        List<NotificationResponse> notificationResponse = notifications
                .stream()
                .map(NotificationTransformer::transformModelToResponse)
                .toList();
        return new ResponseEntity<>(notificationResponse, HttpStatus.OK);
    }

    @PatchMapping("/notification")
    public ResponseEntity<NotificationResponse> update(@RequestBody NotificationRequest notificationRequest) {
        Notification notification = NotificationTransformer.transformRequestToModel(notificationRequest);
        NotificationResponse notificationResponse = NotificationTransformer.transformModelToResponse(notificationService.update(notification));
        return new ResponseEntity<>(notificationResponse, HttpStatus.CREATED);
    }

    @DeleteMapping("/notification/{notification_id}")
    public ResponseEntity<MessageResponse> delete(@PathVariable("notification_id") String notificationId) {
        notificationService.delete(notificationId);
        String message = "Successfully delete notification with id " + notificationId;
        return new ResponseEntity(message, HttpStatus.OK);
    }
}
