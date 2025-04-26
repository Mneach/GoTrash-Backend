package com.gotrash.api.v1.transformer;

import com.gotrash.api.v1.model.Company;
import com.gotrash.api.v1.request.CompanyRequest;
import com.gotrash.api.v1.request.auth.RegisterCompanyRequest;
import com.gotrash.api.v1.response.CompanyResponse;
import com.gotrash.entity.CompanyEntity;

import java.util.UUID;

public class CompanyTransformer {

  public static CompanyEntity transformModelToEntity(Company company) {
    return CompanyEntity.builder()
        .userId(company.getUserId() != null ? UUID.fromString(company.getUserId()) : null)
        .user(company.getUser() != null ? UserTransformer.transformModelToEntity(company.getUser()) : null)
        .name(company.getName())
        .address(company.getAddress())
        .createdAt(company.getCreatedAt())
        .updatedAt(company.getUpdatedAt())
        .build();
  }

  public static Company transformEntityToModel(CompanyEntity companyEntity) {
    return Company.builder()
        .userId(companyEntity.getUserId().toString())
        .user(UserTransformer.transformEntityToModel(companyEntity.getUser()))
        .name(companyEntity.getName())
        .address(companyEntity.getAddress())
        .email(companyEntity.getUser().getEmail())
        .role(companyEntity.getUser().getRole())
        .createdAt(companyEntity.getCreatedAt())
        .updatedAt(companyEntity.getUpdatedAt())
        .build();
  }

  public static Company transformRequestToModel(CompanyRequest companyRequest) {
    return Company.builder()
        .name(companyRequest.getName())
        .password(companyRequest.getPassword())
        .address(companyRequest.getAddress())
        .email(companyRequest.getEmail())
        .role(companyRequest.getRole())
        .build();
  }

  public static Company transformRequestToModel(String userId, CompanyRequest companyRequest) {
    return Company.builder()
        .userId(userId)
        .name(companyRequest.getName())
        .password(companyRequest.getPassword())
        .email(companyRequest.getEmail())
        .role(companyRequest.getRole())
        .address(companyRequest.getAddress())
        .build();
  }

  public static Company transformRequestToModel(RegisterCompanyRequest registerCompanyRequest) {
    return Company.builder()
        .name(registerCompanyRequest.getName())
        .password(registerCompanyRequest.getPassword())
        .email(registerCompanyRequest.getEmail())
        .role(registerCompanyRequest.getRole())
        .address(registerCompanyRequest.getAddress())
        .build();
  }

  public static CompanyResponse transformModelToResponse(Company company) {
    return CompanyResponse.builder()
        .userId(company.getUserId())
        .email(company.getEmail())
        .role(company.getRole())
        .name(company.getName())
        .address(company.getAddress())
        .createdAt(company.getCreatedAt())
        .updatedAt(company.getUpdatedAt())
        .build();
  }
}
