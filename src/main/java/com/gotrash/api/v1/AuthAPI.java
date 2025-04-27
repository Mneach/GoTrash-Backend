package com.gotrash.api.v1;

import com.gotrash.api.response.ApiResponse;
import com.gotrash.api.v1.model.Auth;
import com.gotrash.api.v1.model.Citizen;
import com.gotrash.api.v1.model.Company;
import com.gotrash.api.v1.model.Government;
import com.gotrash.api.v1.model.WasteBank;
import com.gotrash.api.v1.request.auth.AuthRequest;
import com.gotrash.api.v1.request.auth.RegisterCitizenRequest;
import com.gotrash.api.v1.request.auth.RegisterCompanyRequest;
import com.gotrash.api.v1.request.auth.RegisterGovernmentRequest;
import com.gotrash.api.v1.request.auth.RegisterWasteBankRequest;
import com.gotrash.api.v1.response.AuthResponse;
import com.gotrash.api.v1.transformer.AuthTransformer;
import com.gotrash.api.v1.transformer.CitizenTransformer;
import com.gotrash.api.v1.transformer.CompanyTransformer;
import com.gotrash.api.v1.transformer.GovernmentTransformer;
import com.gotrash.api.v1.transformer.WasteBankTransformer;
import com.gotrash.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
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

  @PostMapping("auth/register/citizen")
  @Operation(summary = "API for register a new citizen")
  public ApiResponse<AuthResponse> registerCitizen(@RequestBody RegisterCitizenRequest registerCitizenRequest) {
    Citizen citizen = CitizenTransformer.transformRequestToModel(registerCitizenRequest);
    AuthResponse authResponse = authService.registerCitizen(citizen);

    return new ApiResponse<>(HttpStatus.OK.value(), authResponse);
  }

  @PostMapping("auth/register/waste-bank")
  @Operation(summary = "API for register a new waste bank")
  public ApiResponse<AuthResponse> registerWasteBank(@RequestBody RegisterWasteBankRequest registerWasteBankRequest) {
    WasteBank wasteBank = WasteBankTransformer.transformRequestToModel(registerWasteBankRequest);
    AuthResponse authResponse = authService.registerWasteBank(wasteBank);

    return new ApiResponse<>(HttpStatus.OK.value(), authResponse);
  }

  @PostMapping("auth/register/government")
  @Operation(summary = "API for register a new government")
  public ApiResponse<AuthResponse> registerGovernment(@RequestBody RegisterGovernmentRequest registerGovernmentRequest) {
    Government government = GovernmentTransformer.transformRequestToModel(registerGovernmentRequest);
    AuthResponse authResponse = authService.registerGovernment(government);

    return new ApiResponse<>(HttpStatus.OK.value(), authResponse);
  }

  @PostMapping("auth/register/company")
  @Operation(summary = "API for register a new company")
  public ApiResponse<AuthResponse> registerCompany(@RequestBody RegisterCompanyRequest registerCompanyRequest) {
    Company company = CompanyTransformer.transformRequestToModel(registerCompanyRequest);
    AuthResponse authResponse = authService.registerCompany(company);

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
