package com.gotrash.service;

import com.gotrash.api.v1.model.Notification;
import com.gotrash.api.v1.model.Role;
import com.gotrash.api.v1.model.User;
import com.gotrash.api.v1.transformer.NotificationTransformer;
import com.gotrash.api.v1.transformer.UserTransformer;
import com.gotrash.entity.NotificationEntity;
import com.gotrash.repository.UserRepository;
import com.gotrash.entity.UserEntity;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final RoleService roleService;

    @Transactional
    public User save(User user) {
        Role role = roleService.getRoleByRoleName(user.getRole().getName());
        user.setRole(role);
        UserEntity userEntity = UserTransformer.transformModelToEntity(user);
        return UserTransformer.transformEntityToModel(userRepository.save(userEntity));
    }

    public List<User> getUsers() {
        List<UserEntity> userEntities = userRepository.findAll();

        return userEntities.stream()
            .map(UserTransformer::transformEntityToModel)
            .toList();
    }

    public User getUserByEmail(String email) {
        Optional<UserEntity> userEntityOptional = userRepository.findByEmail(email);
        if (userEntityOptional.isPresent()) {
            return UserTransformer.transformEntityToModel(userEntityOptional.get());
        }

        throw new EntityNotFoundException("User Not Found");
    }

    public User getUserByUserId(String userId) {
        Optional<UserEntity> userEntityOptional = userRepository.findById(UUID.fromString(userId));
        if (userEntityOptional.isPresent()) {
            return UserTransformer.transformEntityToModel(userEntityOptional.get());
        }

        throw new EntityNotFoundException("User Not Found");
    }

    public User getMe() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userId = authentication.getName();

        return getUserByUserId(userId);
    }

    @Transactional
    public User update(User user) {
        if (userRepository.existsById(UUID.fromString(user.getUserId()))) {
            UserEntity userEntity = UserTransformer.transformModelToEntity(user);
            return UserTransformer.transformEntityToModel(userRepository.save(userEntity));
        }

        throw new EntityNotFoundException("User Not Found");
    }

    @Transactional
    public void delete(String userId) {
        if (userRepository.existsById(UUID.fromString(userId))) {
            userRepository.deleteById(UUID.fromString(userId));
            return;
        }

        throw new EntityNotFoundException("User Not Found");
    }

    public boolean userExists(String userId) {
        return userRepository.existsById(UUID.fromString(userId));
    }
}
