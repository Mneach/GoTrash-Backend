package com.gotrash.api.v1;

import com.gotrash.api.response.ApiResponse;
import com.gotrash.api.response.MessageResponse;
import com.gotrash.api.v1.model.Company;
import com.gotrash.api.v1.request.CompanyRequest;
import com.gotrash.api.v1.response.CompanyResponse;
import com.gotrash.api.v1.transformer.CompanyTransformer;
import com.gotrash.service.CompanyService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
@Tag(name = "Company API", description = "API for Company")
public class CompanyAPI {

  private final CompanyService companyService;

  @GetMapping("/companies")
  @Operation(summary = "API to get all company data")
  public ApiResponse<List<CompanyResponse>> getCompanies() {
    List<Company> companies = companyService.getCompanies();
    List<CompanyResponse> companyResponses = companies.stream()
        .map(CompanyTransformer::transformModelToResponse)
        .toList();
    return new ApiResponse<>(HttpStatus.OK.value(), companyResponses);
  }

  @GetMapping("/companies/me")
  @Operation(summary = "API to get current company user")
  public ApiResponse<CompanyResponse> getMe() {
    CompanyResponse companyResponse = CompanyTransformer.transformModelToResponse(
        companyService.getMe()
    );
    return new ApiResponse<>(HttpStatus.OK.value(), companyResponse);
  }

  @GetMapping("/companies/{user_id}")
  @Operation(summary = "API to get company by user id")
  public ApiResponse<CompanyResponse> getCompanyByUserId(@PathVariable("user_id") String userId) {
    CompanyResponse companyResponse = CompanyTransformer.transformModelToResponse(
        companyService.getCompanyByUserId(userId)
    );
    return new ApiResponse<>(HttpStatus.OK.value(), companyResponse);
  }

  @PatchMapping("/companies/{user_id}")
  @Operation(summary = "API to update company")
  public ApiResponse<CompanyResponse> update(@PathVariable("user_id") String userId,
                                                @RequestBody CompanyRequest companyRequest) {
    Company company = CompanyTransformer.transformRequestToModel(userId, companyRequest);
    CompanyResponse companyResponse = CompanyTransformer.transformModelToResponse(companyService.update(company));
    return new ApiResponse<>(HttpStatus.OK.value(), companyResponse);
  }

  @DeleteMapping("/companies/{user_id}")
  @Operation(summary = "API to delete company by user id")
  public ApiResponse<MessageResponse> delete(@PathVariable("user_id") String userId) {
    companyService.delete(userId);
    String message = "Successfully delete company with id " + userId;
    return new ApiResponse<>(HttpStatus.OK.value(), message);
  }
}
