package com.gotrash.service;

import com.gotrash.api.v1.model.User;
import com.gotrash.api.v1.transformer.UserTransformer;
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

    @Transactional
    public User save(User user) {
        UserEntity userEntity = UserTransformer.transformModelToEntity(user);
        userEntity = userRepository.save(userEntity);
        return UserTransformer.transformEntityToModel(userEntity);
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

    public boolean isEmailAlreadyExists(String email) {
        Optional<UserEntity> userEntityOptional = userRepository.findByEmail(email);

        if (userEntityOptional.isPresent()) {
            return true;
        }

        return false;
    }

    public Long countTotalUser() {
        return userRepository.count();
    }
}
