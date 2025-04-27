package com.gotrash.service;

import com.gotrash.api.v1.model.Auth;
import com.gotrash.api.v1.model.Citizen;
import com.gotrash.api.v1.model.Company;
import com.gotrash.api.v1.model.Government;
import com.gotrash.api.v1.model.User;
import com.gotrash.api.v1.model.WasteBank;
import com.gotrash.api.v1.response.AuthResponse;
import com.gotrash.constant.UserRole;
import com.gotrash.util.PasswordGeneratorUtil;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigInteger;

@Service
@AllArgsConstructor
public class AuthService {

  private final UserService userService;
  private final WasteBankService wasteBankService;
  private final GovernmentService governmentService;
  private final CitizenService citizenService;
  private final CompanyService companyService;
  private final JwtService jwtService;
  private final AuthenticationManager authenticationManager;

  @Transactional
  public AuthResponse registerGuest() {
    Long total = userService.countTotalUser();
    String email = "Guest" + total + "@gmail.com";
    String password = PasswordGeneratorUtil.generate(10);

    Citizen citizen = Citizen.builder()
        .email(email)
        .name("Guest")
        .password(password)
        .role(UserRole.GUEST)
        .build();

    citizen.setPhoneNumber("Guest");
    citizen.setCoin(BigInteger.valueOf(0L));

    citizen = citizenService.save(citizen);
    String jwtToken = jwtService.generateToken(citizen.getUser());

    return AuthResponse.builder()
        .token(jwtToken)
        .build();
  }

  @Transactional
  public AuthResponse registerCitizen(Citizen citizen) {

    citizen = citizenService.save(citizen);
    String jwtToken = jwtService.generateToken(citizen.getUser());

    return AuthResponse.builder()
        .token(jwtToken)
        .build();
  }

  @Transactional
  public AuthResponse registerWasteBank(WasteBank wasteBank) {

    if (userService.isEmailAlreadyExists(wasteBank.getEmail())) {
      throw new IllegalArgumentException("Email is already in use.");
    }

    wasteBank = wasteBankService.save(wasteBank);
    String jwtToken = jwtService.generateToken(wasteBank.getUser());

    return AuthResponse.builder()
        .token(jwtToken)
        .build();
  }

  @Transactional
  public AuthResponse registerGovernment(Government government) {

    if (userService.isEmailAlreadyExists(government.getEmail())) {
      throw new IllegalArgumentException("Email is already in use.");
    }

    government = governmentService.save(government);
    String jwtToken = jwtService.generateToken(government.getUser());

    return AuthResponse.builder()
        .token(jwtToken)
        .build();
  }

  @Transactional
  public AuthResponse registerCompany(Company company) {

    if (userService.isEmailAlreadyExists(company.getEmail())) {
      throw new IllegalArgumentException("Email is already in use.");
    }

    company = companyService.save(company);
    String jwtToken = jwtService.generateToken(company.getUser());

    return AuthResponse.builder()
        .token(jwtToken)
        .build();
  }

  public AuthResponse login(Auth auth) {
    authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(
            auth.getEmail(),
            auth.getPassword()
        )
    );

    User user = userService.getUserByEmail(auth.getEmail());
    String jwtToken = jwtService.generateToken(user);

    return AuthResponse.builder()
        .token(jwtToken)
        .build();
  }

}
