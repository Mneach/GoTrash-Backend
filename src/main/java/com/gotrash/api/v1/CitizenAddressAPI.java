package com.gotrash.api.v1;

import com.gotrash.api.response.ApiResponse;
import com.gotrash.api.response.MessageResponse;
import com.gotrash.api.v1.model.CitizenAddress;
import com.gotrash.api.v1.model.Group;
import com.gotrash.api.v1.request.CitizenAddressRequest;
import com.gotrash.api.v1.request.group.GroupRequest;
import com.gotrash.api.v1.response.CitizenAddressResponse;
import com.gotrash.api.v1.response.CitizenResponse;
import com.gotrash.api.v1.response.GroupResponse;
import com.gotrash.api.v1.transformer.CitizenAddressTransformer;
import com.gotrash.api.v1.transformer.GroupTransformer;
import com.gotrash.service.CitizenAddressService;
import com.gotrash.service.CitizenService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
@Tag(name = "Citizen Address API", description = "API for Citizen Address")
public class CitizenAddressAPI {

  private final CitizenAddressService citizenAddressService;

  @PostMapping("/citizens/{citizen_id}/addresses")
  @Operation(summary = "API to create a new citizen address")
  public ApiResponse<CitizenAddressResponse> save(
      @PathVariable("citizen_id") String citizenId,
      @RequestBody CitizenAddressRequest citizenAddressRequest
  ) {
    CitizenAddress citizenAddress = CitizenAddressTransformer.transformRequestToModel(
        citizenId, citizenAddressRequest
    );

    CitizenAddressResponse citizenAddressResponse = CitizenAddressTransformer.transformModelToResponse(
        citizenAddressService.save(citizenAddress)
    );

    return new ApiResponse<>(HttpStatus.CREATED.value(), citizenAddressResponse);
  }

  @GetMapping("/citizens/{citizen_id}/addresses")
  @Operation(summary = "API to get all citizen addresss by citizen id")
  public ApiResponse<List<CitizenAddressResponse>> getAllCitizenAddressByCitizenId(
      @PathVariable("citizen_id") String citizenId
  ) {

    List<CitizenAddress> citizenAddresses = citizenAddressService.getAllAddressesByCitizenId(citizenId);
    List<CitizenAddressResponse> citizenAddressResponses = citizenAddresses
        .stream()
        .map(CitizenAddressTransformer::transformModelToResponse)
        .toList();

    return new ApiResponse<>(HttpStatus.OK.value(), citizenAddressResponses);
  }

  @GetMapping("/citizens/{citizen_id}/addresses/{citizen_address_id}")
  @Operation(summary = "API to get citizen address by citizen_id and citizen_address_id")
  public ApiResponse<CitizenAddressResponse> getCitizenAddressByCitizenIdAndAddressId(
      @PathVariable("citizen_id") String citizenId,
      @PathVariable("citizen_address_id") String citizenAddressId
  ){

    CitizenAddressResponse citizenAddressResponses = CitizenAddressTransformer.transformModelToResponse(
        citizenAddressService.getCitizenAddressByCitizenIdAndAddressId(citizenAddressId, citizenId)
    );

    return new ApiResponse<>(HttpStatus.OK.value(), citizenAddressResponses);
  }

  @PatchMapping("/citizens/{citizen_id}/addresses/{citizen_address_id}")
  @Operation(summary = "API to update citizen address")
  public ApiResponse<CitizenAddressResponse> updateCitizenAddressByCitizenIdAndAddressId(
      @PathVariable("citizen_id") String citizenId,
      @PathVariable("citizen_address_id") String citizenAddressId,
      @RequestBody CitizenAddressRequest citizenAddressRequest
  ){

    CitizenAddress citizenAddress = CitizenAddressTransformer.transformRequestToModel(
        citizenId, citizenAddressId, citizenAddressRequest
    );

    CitizenAddressResponse citizenAddressResponse = CitizenAddressTransformer.transformModelToResponse(
        citizenAddressService.update(citizenAddress)
    );

    return new ApiResponse<>(HttpStatus.CREATED.value(), citizenAddressResponse);
  }

  @DeleteMapping("/citizens/{citizen_id}/addresses/{citizen_address_id}")
  @Operation(summary = "API to create delete citizen address")
  public ApiResponse<MessageResponse> deleteAddressByAddressId(
      @PathVariable("citizen_id") String citizenId,
      @PathVariable("citizen_address_id") String citizenAddressId
  ){

    citizenAddressService.delete(citizenId, citizenAddressId);

    String message = "Address deleted successfully with ID " + citizenAddressId;
    return new ApiResponse<>(HttpStatus.OK.value(), message);
  }
}
