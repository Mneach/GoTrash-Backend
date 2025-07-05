package com.gotrash.api.v1;

import com.gotrash.api.response.ApiResponse;
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
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
@Tag(name = "Waste Bank API", description = "API for Waste Bank")
public class WasteBankAPI {

  private final WasteBankService wasteBankService;

  @GetMapping(value = "/waste-banks")
  @Operation(summary = "API to get all waste bank data")
  public ApiResponse<List<WasteBankResponse>> getWasteBanks() {
    List<WasteBank> wasteBanks = wasteBankService.getWasteBanks();
    List<WasteBankResponse> wasteBankResponses = wasteBanks.stream()
        .map(WasteBankTransformer::transformModelToResponse)
        .toList();
    return new ApiResponse<>(HttpStatus.OK.value(), wasteBankResponses);
  }

  @GetMapping(value = "/waste-banks/region/{region_name}")
  @Operation(summary = "API to get all waste bank data by region")
  public ApiResponse<List<WasteBankResponse>> getAllWasteBankByRegion(@PathVariable("region_name") String region) {
    List<WasteBank> wasteBanks = wasteBankService.getAllWasteBankByRegion(region);
    List<WasteBankResponse> wasteBankResponses = wasteBanks.stream()
        .map(WasteBankTransformer::transformModelToResponse)
        .toList();
    return new ApiResponse<>(HttpStatus.OK.value(), wasteBankResponses);
  }

  @GetMapping("/waste-banks/me")
  @Operation(summary = "API to get current waste bank user")
  public ApiResponse<WasteBankResponse> getMe() {
    WasteBank wasteBank = wasteBankService.getMe();
    WasteBankResponse wasteBankResponse = WasteBankTransformer.transformModelToResponse(wasteBank);
    return new ApiResponse<>(HttpStatus.OK.value(), wasteBankResponse);
  }

  @GetMapping("/waste-banks/{user_id}")
  @Operation(summary = "API to get waste bank by user id")
  public ApiResponse<WasteBankResponse> getWasteBankByUserId(@PathVariable("user_id") String userId) {

    WasteBank wasteBank = wasteBankService.getWasteBankByUserId(userId);
    WasteBankResponse wasteBankResponse = WasteBankTransformer.transformModelToResponse(wasteBank);
    return new ApiResponse<>(HttpStatus.OK.value(), wasteBankResponse);
  }

  @PatchMapping(value = "/waste-banks/{user_id}", consumes = {"multipart/form-data"})
  @Operation(summary = "API to update waste bank")
  public ApiResponse<WasteBankResponse> update(@PathVariable("user_id") String userId,
                                               @ModelAttribute  WasteBankRequest wasteBankRequest) {
    WasteBank wasteBank = WasteBankTransformer.transformRequestToModel(userId, wasteBankRequest);
    wasteBank = wasteBankService.update(wasteBank, wasteBankRequest.getImageFile());
    WasteBankResponse wasteBankResponse = WasteBankTransformer.transformModelToResponse(wasteBank);
    return new ApiResponse<>(HttpStatus.OK.value(), wasteBankResponse);
  }

  @DeleteMapping("/waste-banks/{user_id}")
  @Operation(summary = "API to delete waste bank by user id")
  public ApiResponse<MessageResponse> delete(@PathVariable("user_id") String userId) {
    wasteBankService.delete(userId);
    String message = "Successfully delete waste bank with id " + userId;
    return new ApiResponse<>(HttpStatus.OK.value(), message);
  }
}
