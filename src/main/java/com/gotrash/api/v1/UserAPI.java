package com.gotrash.api.v1;

import com.gotrash.api.response.MessageResponse;
import com.gotrash.api.v1.model.User;
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

    @PostMapping("/user")
    public ResponseEntity<User> save(@RequestBody User user) {
        User savedUser = userService.save(user);
        return new ResponseEntity<>(savedUser, HttpStatus.CREATED);
    }

    @GetMapping("/user/{user_id}")
    public ResponseEntity<User> getUserById(@PathVariable("user_id") String userId) {
        User user = userService.getUserByUserId(userId);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @PatchMapping("/user")
    public ResponseEntity<User> update(@RequestBody User user) {
        User savedUser = userService.update(user);
        return new ResponseEntity<>(savedUser, HttpStatus.OK);
    }

    @DeleteMapping("/user/{user_id}")
    public ResponseEntity<MessageResponse> delete(@PathVariable("user_id") String userId) {
        userService.delete(userId);
        String message = "Successfully delete user with id " + userId;
        return new ResponseEntity(message, HttpStatus.OK);
    }
}
