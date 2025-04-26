package com.gotrash.api.v1;


import com.gotrash.api.response.MessageResponse;
import com.gotrash.api.v1.model.Citizen;
import com.gotrash.api.v1.request.CitizenRequest;
import com.gotrash.api.v1.response.CitizenResponse;
import com.gotrash.api.v1.transformer.CitizenTransformer;
import com.gotrash.service.CitizenService;
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
@Tag(name = "Citizen API", description = "API for Citizen")
public class CitizenAPI {
  private final CitizenService citizenService;

  @GetMapping("/citizens")
  @Operation(summary = "API to get all citizen data")
  public ResponseEntity<List<CitizenResponse>> getCitizens() {
    List<Citizen> citizen = citizenService.getCitizens();
    List<CitizenResponse> citizenResponses = citizen.stream()
        .map(CitizenTransformer::transformModelToResponse)
        .toList();
    return new ResponseEntity<>(citizenResponses, HttpStatus.OK);
  }

  @GetMapping("/citizens/me")
  @Operation(summary = "API to get current citizen user")
  public ResponseEntity<CitizenResponse> getMe() {
    CitizenResponse citizenResponse = CitizenTransformer.transformModelToResponse(
        citizenService.getMe()
    );
    return new ResponseEntity<>(citizenResponse, HttpStatus.OK);
  }

  @GetMapping("/citizens/{user_id}")
  @Operation(summary = "API to get citizen by user id")
  public ResponseEntity<CitizenResponse> getCitizenByUserId(@PathVariable("user_id") String userId) {
    CitizenResponse citizenResponse = CitizenTransformer.transformModelToResponse(
        citizenService.getCitizenByUserId(userId)
    );
    return new ResponseEntity<>(citizenResponse, HttpStatus.OK);
  }

  @PatchMapping("/citizens/{user_id}")
  @Operation(summary = "API to update citizen")
  public ResponseEntity<CitizenResponse> update(@PathVariable("user_id") String userId, @RequestBody CitizenRequest citizenRequest) {
    Citizen citizen = CitizenTransformer.transformRequestToModel(userId, citizenRequest);
    CitizenResponse citizenResponse = CitizenTransformer.transformModelToResponse(citizenService.update(citizen));
    return new ResponseEntity<>(citizenResponse, HttpStatus.OK);
  }

  @DeleteMapping("/citizens/{user_id}")
  @Operation(summary = "API to delete citizen by user id")
  public ResponseEntity<MessageResponse> delete(@PathVariable("user_id") String userId) {
    citizenService.delete(userId);
    String message = "Successfully delete citizen with id " + userId;
    return new ResponseEntity(message, HttpStatus.OK);
  }
}
