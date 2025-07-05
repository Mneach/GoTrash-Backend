package com.gotrash.api.v1;

import com.gotrash.api.response.ApiResponse;
import com.gotrash.api.response.MessageResponse;
import com.gotrash.api.v1.model.User;
import com.gotrash.api.v1.request.UserRequest;
import com.gotrash.api.v1.response.UserResponse;
import com.gotrash.api.v1.transformer.UserTransformer;
import com.gotrash.service.AdminService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
@Tag(name = "Admin API", description = "API for Admin")
public class AdminAPI {

  private final AdminService adminService;

  @GetMapping(value = "admins")
  @Operation(summary = "API to get all admin data")
  public ApiResponse<List<UserResponse>> getAdmins() {
    List<User> users = adminService.getAdmins();
    List<UserResponse> userResponses = users.stream()
        .map(UserTransformer::transformModelToResponse)
        .toList();
    
    return new ApiResponse<>(HttpStatus.OK.value(), userResponses);
  }

  @GetMapping("admin/me")
  @Operation(summary = "API to get current admin")
  public ApiResponse<UserResponse> getMe() {
    User user = adminService.getMe();
    UserResponse userResponse = UserTransformer.transformModelToResponse(user);
    return new ApiResponse<>(HttpStatus.OK.value(), userResponse);
  }

  @GetMapping("admins/{user_id}")
  @Operation(summary = "API to get admin by user id")
  public ApiResponse<UserResponse> getWasteBankByUserId(@PathVariable("user_id") String userId) {

    User user = adminService.getAdminByUserId(userId);
    UserResponse userResponse = UserTransformer.transformModelToResponse(user);
    return new ApiResponse<>(HttpStatus.OK.value(), userResponse);
  }

  @PatchMapping(value = "admins/{user_id}")
  @Operation(summary = "API to update admin")
  public ApiResponse<UserResponse> update(@PathVariable("user_id") String userId,
                                          @ModelAttribute UserRequest userRequest) {
    User user = UserTransformer.transformRequestToModel(userId, userRequest);
    user = adminService.update(user);
    UserResponse userResponse = UserTransformer.transformModelToResponse(user);
    return new ApiResponse<>(HttpStatus.OK.value(), userResponse);
  }

  @DeleteMapping("admins/{user_id}")
  @Operation(summary = "API to delete admin by user id")
  public ApiResponse<MessageResponse> delete(@PathVariable("user_id") String userId) {
    adminService.delete(userId);
    String message = "Successfully delete admin with id " + userId;
    return new ApiResponse<>(HttpStatus.OK.value(), message);
  }
}

