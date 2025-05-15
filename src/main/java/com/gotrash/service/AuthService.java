package com.gotrash.service;

import com.gotrash.api.v1.model.Auth;
import com.gotrash.api.v1.model.Citizen;
import com.gotrash.api.v1.model.Company;
import com.gotrash.api.v1.model.Government;
import com.gotrash.api.v1.model.User;
import com.gotrash.api.v1.model.WasteBank;
import com.gotrash.api.v1.response.AuthResponse;
import com.gotrash.constant.UserRole;
import com.gotrash.exception.rest.BadRequestException;
import com.gotrash.helper.FileUploadHelper;
import com.gotrash.util.PasswordGeneratorUtil;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

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
  private final FileUploadHelper fileUploadHelper;

  @Transactional
  public AuthResponse registerGuest() {
    Long totalUser = userService.countTotalUser();
    String email = "Guest" + totalUser + "@gmail.com";
    String password = PasswordGeneratorUtil.generate(10);

    Citizen citizen = Citizen.builder()
        .email(email)
        .name("Guest")
        .password(password)
        .role(UserRole.GUEST)
        .build();

    citizen.setImageUrl("https://www.twtf.org.uk/wp-content/uploads/2024/01/dummy-image.jpg");
    citizen.setPhoneNumber(totalUser.toString());
    citizen.setCurrentStreak(0);
    citizen.setLongestStreak(0);
    citizen.setRating(BigInteger.valueOf(0));
    citizen.setCoin(BigInteger.valueOf(0));
    citizen.setBleId(BigInteger.valueOf(totalUser + 1));

    citizen = citizenService.save(citizen);
    String jwtToken = jwtService.generateToken(citizen.getUser());

    return AuthResponse.builder()
        .token(jwtToken)
        .role(citizen.getRole())
        .build();
  }

  @Transactional
  public AuthResponse registerCitizen(Citizen citizen, MultipartFile imageFile) {

    if (userService.isEmailAlreadyExists(citizen.getEmail())) {
      throw new BadRequestException("Email is already in use.");
    } else if (citizenService.isPhoneNumberAlreadyExists(citizen.getPhoneNumber())) {
      throw new BadRequestException("Phone Number is already in use.");
    }

    Long totalUser = userService.countTotalUser();

    if (imageFile != null) {
      try {
        String filePath = fileUploadHelper.uploadFile("citizens", citizen.getEmail(), imageFile, null);
        String imageUrl = fileUploadHelper.generateFileUrl(filePath);
        citizen.setImageUrl(imageUrl);
      } catch (Exception e) {
        throw new RuntimeException(e.getMessage());
      }
    } else {
      citizen.setImageUrl("https://www.twtf.org.uk/wp-content/uploads/2024/01/dummy-image.jpg");
    }

    citizen.setCurrentStreak(0);
    citizen.setLongestStreak(0);
    citizen.setRating(BigInteger.valueOf(0));
    citizen.setCoin(BigInteger.valueOf(0));
    citizen.setBleId(BigInteger.valueOf(totalUser + 1));

    citizen = citizenService.save(citizen);
    String jwtToken = jwtService.generateToken(citizen.getUser());

    return AuthResponse.builder()
        .token(jwtToken)
        .role(citizen.getRole())
        .build();
  }

  @Transactional
  public AuthResponse registerWasteBank(WasteBank wasteBank, MultipartFile imageFile) {

    if (userService.isEmailAlreadyExists(wasteBank.getEmail())) {
      throw new BadRequestException("Email is already in use.");
    }

    if (imageFile != null) {
      try {
        String filePath = fileUploadHelper.uploadFile("wastebanks", wasteBank.getEmail(), imageFile, null);
        String imageUrl = fileUploadHelper.generateFileUrl(filePath);
        wasteBank.setImageUrl(imageUrl);
      } catch (Exception e) {
        throw new RuntimeException(e.getMessage());
      }
    } else {
      wasteBank.setImageUrl("https://www.twtf.org.uk/wp-content/uploads/2024/01/dummy-image.jpg");
    }

    wasteBank = wasteBankService.save(wasteBank);
    String jwtToken = jwtService.generateToken(wasteBank.getUser());

    return AuthResponse.builder()
        .token(jwtToken)
        .role(wasteBank.getRole())
        .build();
  }

  @Transactional
  public AuthResponse registerGovernment(Government government) {

    if (userService.isEmailAlreadyExists(government.getEmail())) {
      throw new BadRequestException("Email is already in use.");
    }

    government = governmentService.save(government);
    String jwtToken = jwtService.generateToken(government.getUser());

    return AuthResponse.builder()
        .token(jwtToken)
        .role(government.getRole())
        .build();
  }

  @Transactional
  public AuthResponse registerCompany(Company company) {

    if (userService.isEmailAlreadyExists(company.getEmail())) {
      throw new BadRequestException("Email is already in use.");
    }

    company = companyService.save(company);
    String jwtToken = jwtService.generateToken(company.getUser());

    return AuthResponse.builder()
        .token(jwtToken)
        .role(company.getRole())
        .build();
  }

  public AuthResponse login(Auth auth) {
    authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(
            auth.getEmail(),
            auth.getPassword()));

    User user = userService.getUserByEmail(auth.getEmail());
    String jwtToken = jwtService.generateToken(user);

    return AuthResponse.builder()
        .token(jwtToken)
        .role(user.getRole())
        .build();
  }

}
