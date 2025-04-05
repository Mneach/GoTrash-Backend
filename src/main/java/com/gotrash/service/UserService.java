package com.gotrash.service;

import com.gotrash.api.v1.model.User;
import com.gotrash.api.v1.transformer.UserTransformer;
import com.gotrash.repository.UserRepository;
import com.gotrash.entity.UserEntity;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    @Transactional
    public User save(User user) {
        UserEntity userEntity = UserTransformer.transformModelToEntity(user);
        return UserTransformer.transformEntityToModel(userRepository.save(userEntity));
    }

    public User getUserByUserId(String userId) {
        Optional<UserEntity> userEntityOptional = userRepository.findById(userId);
        if (userEntityOptional.isPresent()) {
            return UserTransformer.transformEntityToModel(userEntityOptional.get());
        }

        throw new EntityNotFoundException("User Not Found");
    }

    @Transactional
    public User update(User user) {
        if (userRepository.existsById(user.getUserId())) {
            UserEntity userEntity = UserTransformer.transformModelToEntity(user);
            return UserTransformer.transformEntityToModel(userRepository.save(userEntity));
        }

        throw new EntityNotFoundException("User Not Found");
    }

    @Transactional
    public void delete(String userId) {
        if (userRepository.existsById(userId)) {
            userRepository.deleteById(userId);
        }

        throw new EntityNotFoundException("User Not Found");
    }
}
