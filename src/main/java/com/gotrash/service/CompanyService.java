package com.gotrash.service;

import com.gotrash.api.v1.model.Company;
import com.gotrash.api.v1.model.User;
import com.gotrash.api.v1.transformer.CompanyTransformer;
import com.gotrash.api.v1.transformer.UserTransformer;
import com.gotrash.entity.CompanyEntity;
import com.gotrash.entity.UserEntity;
import com.gotrash.repository.CompanyRepository;
import com.gotrash.repository.UserRepository;
import com.gotrash.util.AuthorityUtil;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CompanyService {
  private final CompanyRepository companyRepository;
  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;

  @Transactional
  public Company save(Company company) {

    User user = User.builder()
        .email(company.getEmail())
        .role(company.getRole())
        .password(passwordEncoder.encode(company.getPassword()))
        .build();

    UserEntity userEntity = userRepository.save(UserTransformer.transformModelToEntity(user));

    CompanyEntity companyEntity = CompanyTransformer.transformModelToEntity(company);
    companyEntity.setUser(userEntity);

    return CompanyTransformer.transformEntityToModel(
        companyRepository.save(companyEntity)
    );
  }

  public List<Company> getCompanies() {
    List<CompanyEntity> companyEntities = companyRepository.findAll();

    return companyEntities.stream()
        .map(CompanyTransformer::transformEntityToModel)
        .toList();
  }

  public Company getCompanyByUserId(String userId) {
    Optional<CompanyEntity> trashBinEntityOptional = companyRepository.findByUser_UserId(UUID.fromString(userId));

    if (trashBinEntityOptional.isEmpty()) {
      throw new EntityNotFoundException("Company with User ID " + userId + " Not Found");
    }

    return CompanyTransformer.transformEntityToModel(trashBinEntityOptional.get());
  }

  public Company getMe() {
    String userId = AuthorityUtil.getCurrentUserId();
    return getCompanyByUserId(userId);
  }

  @Transactional
  public Company update(Company company) {

    CompanyEntity companyEntity = companyRepository.findById(UUID.fromString(company.getUserId()))
        .orElseThrow(() -> new EntityNotFoundException("Company with ID " + company.getUserId() + " not found"));

    UserEntity userEntity = userRepository.findById(UUID.fromString(company.getUserId()))
        .orElseThrow(() -> new EntityNotFoundException("User with ID " + company.getUserId() + " not found"));

    userEntity.setEmail(company.getEmail());
    userEntity.setRole(company.getRole());
    userEntity.setPassword(passwordEncoder.encode(company.getPassword()));
    userEntity = userRepository.save(userEntity);

    companyEntity.setUser(userEntity);
    companyEntity.setAddress(company.getAddress());
    companyEntity.setName(company.getName());
    companyEntity.setUpdatedAt(LocalDateTime.now());

    return CompanyTransformer.transformEntityToModel(
        companyRepository.save(companyEntity)
    );
  }

  @Transactional
  public void delete(String companyId) {
    if (!companyRepository.existsById(UUID.fromString(companyId))) {
      throw new EntityNotFoundException("Company with ID " + companyId + " Not Found");
    }

    companyRepository.deleteById(UUID.fromString(companyId));
  }
}
