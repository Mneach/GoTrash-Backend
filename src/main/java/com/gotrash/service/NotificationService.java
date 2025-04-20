package com.gotrash.service;

import com.gotrash.api.v1.model.Group;
import com.gotrash.api.v1.model.Notification;
import com.gotrash.api.v1.model.User;
import com.gotrash.api.v1.transformer.GroupTransformer;
import com.gotrash.api.v1.transformer.NotificationTransformer;
import com.gotrash.api.v1.transformer.UserTransformer;
import com.gotrash.entity.GroupEntity;
import com.gotrash.entity.NotificationEntity;
import com.gotrash.entity.UserEntity;
import com.gotrash.repository.NotificationRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class NotificationService {

    private final NotificationRepository notificationRepository;
    private final UserService userService;

    @Transactional
    public Notification save(Notification notification) {
        User user = userService.getUserByUserId(notification.getUser().getUserId());
        notification.setUser(user);
        NotificationEntity notificationEntity = NotificationTransformer.transformModelToEntity(notification);
        return NotificationTransformer.transformEntityToModel(notificationRepository.save(notificationEntity));
    }

    public List<Notification> getNotifications() {
        List<NotificationEntity> notificationEntities = notificationRepository.findAll();

        return notificationEntities.stream()
            .map(NotificationTransformer::transformEntityToModel)
            .toList();
    }

    public Notification getNotificationByNotificationId(String notificationId) {
        Optional<NotificationEntity> notificationEntityOptional = notificationRepository.findById(UUID.fromString(notificationId));

        if (notificationEntityOptional.isPresent()) {
            return NotificationTransformer.transformEntityToModel(notificationEntityOptional.get());
        }

        throw new EntityNotFoundException("Notification Data With ID " + notificationId + " Not Found");
    }

    public List<Notification> getNotificationByUserId(String userId) {
        List<NotificationEntity> notificationEntities = notificationRepository.findAllByUser_UserId(UUID.fromString(userId));

        return notificationEntities
                .stream()
                .map(NotificationTransformer::transformEntityToModel)
                .toList();
    }

    @Transactional
    public Notification update(Notification notification) {
        if (notificationRepository.existsById(UUID.fromString(notification.getNotificationId()))) {
            User user = userService.getUserByUserId(notification.getUser().getUserId());
            notification.setUser(user);
            NotificationEntity notificationEntity = NotificationTransformer.transformModelToEntity(notification);
            return NotificationTransformer.transformEntityToModel(notificationRepository.save(notificationEntity));
        }

        throw new EntityNotFoundException("Notification Data With ID " + notification.getNotificationId() + " Not Found");
    }

    @Transactional
    public void delete(String notificationId) {
        if (notificationRepository.existsById(UUID.fromString(notificationId))) {
            notificationRepository.deleteById(UUID.fromString(notificationId));
            return;
        }

        throw new EntityNotFoundException("Notification Data With ID " + notificationId + " Not Found");

    }
}
