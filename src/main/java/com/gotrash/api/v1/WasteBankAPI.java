package com.gotrash.api.v1;

import com.gotrash.api.response.MessageResponse;
import com.gotrash.api.v1.model.WasteBank;
import com.gotrash.api.v1.request.WasteBankRequest;
import com.gotrash.api.v1.response.WasteBankResponse;
import com.gotrash.api.v1.transformer.WasteBankTransformer;
import com.gotrash.service.WasteBankService;
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
@Tag(name = "Waste Bank API", description = "API for Waste Bank")
public class WasteBankAPI {

  private final WasteBankService wasteBankService;

  @GetMapping("/waste-banks")
  @Operation(summary = "API to get all waste bank data")
  public ResponseEntity<List<WasteBankResponse>> getWasteBanks() {
    List<WasteBank> wasteBanks = wasteBankService.getWasteBanks();
    List<WasteBankResponse> wasteBankResponses = wasteBanks.stream()
        .map(WasteBankTransformer::transformModelToResponse)
        .toList();
    return new ResponseEntity<>(wasteBankResponses, HttpStatus.OK);
  }

  @GetMapping("/waste-banks/me")
  @Operation(summary = "API to get current waste bank user")
  public ResponseEntity<WasteBankResponse> getMe() {
    WasteBankResponse wasteBankResponse = WasteBankTransformer.transformModelToResponse(
        wasteBankService.getMe()
    );
    return new ResponseEntity<>(wasteBankResponse, HttpStatus.OK);
  }

  @GetMapping("/waste-banks/{user_id}")
  @Operation(summary = "API to get waste bank by user id")
  public ResponseEntity<WasteBankResponse> getWasteBankByUserId(@PathVariable("user_id") String userId) {
    WasteBankResponse wasteBankResponse = WasteBankTransformer.transformModelToResponse(
        wasteBankService.getWasteBankByUserId(userId)
    );
    return new ResponseEntity<>(wasteBankResponse, HttpStatus.OK);
  }

  @PatchMapping("/waste-banks/{user_id}")
  @Operation(summary = "API to update waste bank")
  public ResponseEntity<WasteBankResponse> update(@PathVariable("user_id") String userId,
                                                  @RequestBody WasteBankRequest wasteBankRequest) {
    WasteBank wasteBank = WasteBankTransformer.transformRequestToModel(userId, wasteBankRequest);
    WasteBankResponse wasteBankResponse = WasteBankTransformer.transformModelToResponse(wasteBankService.update(wasteBank));
    return new ResponseEntity<>(wasteBankResponse, HttpStatus.OK);
  }

  @DeleteMapping("/waste-banks/{user_id}")
  @Operation(summary = "API to delete waste bank by user id")
  public ResponseEntity<MessageResponse> delete(@PathVariable("user_id") String userId) {
    wasteBankService.delete(userId);
    String message = "Successfully delete waste bank with id " + userId;
    return new ResponseEntity(message, HttpStatus.OK);
  }
}
