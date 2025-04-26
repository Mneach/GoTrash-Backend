package com.gotrash.api.v1;


import com.gotrash.api.response.MessageResponse;
import com.gotrash.api.v1.model.Government;
import com.gotrash.api.v1.request.GovernmentRequest;
import com.gotrash.api.v1.response.GovernmentResponse;
import com.gotrash.api.v1.transformer.GovernmentTransformer;
import com.gotrash.service.GovernmentService;
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
@Tag(name = "Government API", description = "API for Government")
public class GovernmentAPI {
  private final GovernmentService governmentService;

  @GetMapping("/governments")
  @Operation(summary = "API to get all government data")
  public ResponseEntity<List<GovernmentResponse>> getGovernments() {
    List<Government> governments = governmentService.getGovernments();
    List<GovernmentResponse> governmentResponses = governments.stream()
        .map(GovernmentTransformer::transformModelToResponse)
        .toList();
    return new ResponseEntity<>(governmentResponses, HttpStatus.OK);
  }

  @GetMapping("/governments/me")
  @Operation(summary = "API to get current government user")
  public ResponseEntity<GovernmentResponse> getMe() {
    GovernmentResponse governmentResponse = GovernmentTransformer.transformModelToResponse(
        governmentService.getMe()
    );
    return new ResponseEntity<>(governmentResponse, HttpStatus.OK);
  }

  @GetMapping("/governments/{user_id}")
  @Operation(summary = "API to get government by user_id")
  public ResponseEntity<GovernmentResponse> getGovernmentByUserId(@PathVariable("user_id") String userId) {
    GovernmentResponse governmentResponse = GovernmentTransformer.transformModelToResponse(
        governmentService.getGovernmentByUserId(userId)
    );
    return new ResponseEntity<>(governmentResponse, HttpStatus.OK);
  }

  @PatchMapping("/governments/{user_id}")
  @Operation(summary = "API to update government")
  public ResponseEntity<GovernmentResponse> update(@PathVariable("user_id") String userId,
                                                   @RequestBody GovernmentRequest governmentRequest) {
    Government government = GovernmentTransformer.transformRequestToModel(userId, governmentRequest);
    GovernmentResponse governmentResponse = GovernmentTransformer.transformModelToResponse(governmentService.update(government));
    return new ResponseEntity<>(governmentResponse, HttpStatus.OK);
  }

  @DeleteMapping("/governments/{user_id}")
  @Operation(summary = "API to delete government by user_id")
  public ResponseEntity<MessageResponse> delete(@PathVariable("user_id") String userId) {
    governmentService.delete(userId);
    String message = "Successfully delete government with id " + userId;
    return new ResponseEntity(message, HttpStatus.OK);
  }
}
