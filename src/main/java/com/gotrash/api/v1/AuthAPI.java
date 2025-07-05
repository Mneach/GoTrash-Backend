package com.gotrash.api.v1;

import com.gotrash.api.response.ApiResponse;
import com.gotrash.api.v1.model.Auth;
import com.gotrash.api.v1.model.Citizen;
import com.gotrash.api.v1.model.User;
import com.gotrash.api.v1.model.WasteBank;
import com.gotrash.api.v1.request.UserRequest;
import com.gotrash.api.v1.request.auth.AuthRequest;
import com.gotrash.api.v1.request.auth.RegisterCitizenRequest;
import com.gotrash.api.v1.request.auth.RegisterWasteBankRequest;
import com.gotrash.api.v1.response.AuthResponse;
import com.gotrash.api.v1.transformer.AuthTransformer;
import com.gotrash.api.v1.transformer.CitizenTransformer;
import com.gotrash.api.v1.transformer.UserTransformer;
import com.gotrash.api.v1.transformer.WasteBankTransformer;
import com.gotrash.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
@Tag(name = "Auth API", description = "API for authentication and authorization")
public class AuthAPI {

  private final AuthService authService;

  @PostMapping("auth/register/guest")
  @Operation(summary = "API for register a new guest")
  public ApiResponse<AuthResponse> registerGuest() {
    AuthResponse authResponse = authService.registerGuest();
    return new ApiResponse<>(HttpStatus.OK.value(), authResponse);
  }

  @PostMapping(value = "auth/register/citizen", consumes = {"multipart/form-data"})
  @Operation(summary = "API for register a new citizen")
  public ApiResponse<AuthResponse> registerCitizen(@ModelAttribute RegisterCitizenRequest registerCitizenRequest) {
    Citizen citizen = CitizenTransformer.transformRequestToModel(registerCitizenRequest);
    AuthResponse authResponse = authService.registerCitizen(citizen, registerCitizenRequest.getImageFile());

    return new ApiResponse<>(HttpStatus.OK.value(), authResponse);
  }

  @PostMapping(value = "auth/register/waste-bank", consumes = {"multipart/form-data"})
  @Operation(summary = "API for register a new waste bank")
  public ApiResponse<AuthResponse> registerWasteBank(@ModelAttribute RegisterWasteBankRequest registerWasteBankRequest) {
    WasteBank wasteBank = WasteBankTransformer.transformRequestToModel(registerWasteBankRequest);
    AuthResponse authResponse = authService.registerWasteBank(wasteBank, registerWasteBankRequest.getImageFile());

    return new ApiResponse<>(HttpStatus.OK.value(), authResponse);
  }

  @PostMapping(value = "auth/register/waste-bank")
  @Operation(summary = "API for register a new waste bank")
  public ApiResponse<AuthResponse> registerAdmin(@ModelAttribute UserRequest userRequest) {
    User user = UserTransformer.transformRequestToModel(userRequest);
    AuthResponse authResponse = authService.registerAdmin(user);

    return new ApiResponse<>(HttpStatus.OK.value(), authResponse);
  }

  @PostMapping("auth/login")
  @Operation(summary = "API for login to the application")
  public ApiResponse<AuthResponse> auth(@RequestBody AuthRequest authRequest) {
    Auth auth = AuthTransformer.transformRequestToModel(authRequest);
    AuthResponse authResponse = authService.login(auth);

    return new ApiResponse<>(HttpStatus.OK.value(), authResponse);
  }

  @Operation(
      summary = "API for logout user",
      description = "Performs logout using Spring Security's filter chain and clears the SecurityContext."
  )
  @PostMapping("/logout")
  public void logoutDoc() {
    // No implementation — this is for documentation only
  }
}
