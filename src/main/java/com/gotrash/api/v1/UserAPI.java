package com.gotrash.api.v1;

import com.gotrash.api.response.MessageResponse;
import com.gotrash.api.v1.model.Role;
import com.gotrash.api.v1.model.User;
import com.gotrash.api.v1.request.UserRequest;
import com.gotrash.api.v1.response.UserResponse;
import com.gotrash.api.v1.transformer.UserTransformer;
import com.gotrash.service.RoleService;
import com.gotrash.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("v1")
public class UserAPI {
    private final UserService userService;

    @GetMapping("/users/{user_id}")
    public ResponseEntity<UserResponse> getUserById(@PathVariable("user_id") String userId) {
        User user = userService.getUserByUserId(userId);
        UserResponse userResponse = UserTransformer.transformModelToResponse(user);
        return new ResponseEntity<>(userResponse, HttpStatus.OK);
    }

    @GetMapping("/users/me")
    public ResponseEntity<UserResponse> getMe() {
        User user = userService.getMe();
        UserResponse userResponse = UserTransformer.transformModelToResponse(user);
        return new ResponseEntity<>(userResponse, HttpStatus.OK);
    }

    @PatchMapping("/users")
    public ResponseEntity<UserResponse> update(@RequestBody UserRequest userRequest) {
        User user = UserTransformer.transformRequestToModel(userRequest);
        UserResponse userResponse = UserTransformer.transformModelToResponse(userService.update(user));
        return new ResponseEntity<>(userResponse, HttpStatus.OK);
    }

    @DeleteMapping("/users/{user_id}")
    public ResponseEntity<MessageResponse> delete(@PathVariable("user_id") String userId) {
        userService.delete(userId);
        String message = "Successfully delete user with id " + userId;
        MessageResponse messageResponse = new MessageResponse(message);
        return new ResponseEntity<>(messageResponse, HttpStatus.OK);
    }
}
