package com.gotrash.api.v1;

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
import org.springframework.http.ResponseEntity;
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
  public ResponseEntity<List<CompanyResponse>> getCompanies() {
    List<Company> companies = companyService.getCompanies();
    List<CompanyResponse> companyResponses = companies.stream()
        .map(CompanyTransformer::transformModelToResponse)
        .toList();
    return new ResponseEntity<>(companyResponses, HttpStatus.OK);
  }

  @GetMapping("/companies/me")
  @Operation(summary = "API to get current company user")
  public ResponseEntity<CompanyResponse> getMe() {
    CompanyResponse companyResponse = CompanyTransformer.transformModelToResponse(
        companyService.getMe()
    );
    return new ResponseEntity<>(companyResponse, HttpStatus.OK);
  }

  @GetMapping("/companies/{user_id}")
  @Operation(summary = "API to get company by user id")
  public ResponseEntity<CompanyResponse> getCompanyByUserId(@PathVariable("user_id") String userId) {
    CompanyResponse companyResponse = CompanyTransformer.transformModelToResponse(
        companyService.getCompanyByUserId(userId)
    );
    return new ResponseEntity<>(companyResponse, HttpStatus.OK);
  }

  @PatchMapping("/companies/{user_id}")
  @Operation(summary = "API to update company")
  public ResponseEntity<CompanyResponse> update(@PathVariable("user_id") String userId,
                                                @RequestBody CompanyRequest companyRequest) {
    Company company = CompanyTransformer.transformRequestToModel(userId, companyRequest);
    CompanyResponse companyResponse = CompanyTransformer.transformModelToResponse(companyService.update(company));
    return new ResponseEntity<>(companyResponse, HttpStatus.OK);
  }

  @DeleteMapping("/companies/{user_id}")
  @Operation(summary = "API to delete company by user id")
  public ResponseEntity<MessageResponse> delete(@PathVariable("user_id") String userId) {
    companyService.delete(userId);
    String message = "Successfully delete company with id " + userId;
    return new ResponseEntity(message, HttpStatus.OK);
  }
}
