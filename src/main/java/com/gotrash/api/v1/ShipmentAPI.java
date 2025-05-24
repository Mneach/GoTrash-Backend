package com.gotrash.api.v1;

import com.gotrash.api.response.ApiResponse;
import com.gotrash.api.response.MessageResponse;
import com.gotrash.api.v1.model.Shipment;
import com.gotrash.api.v1.request.ShipmentRequest;
import com.gotrash.api.v1.response.ShipmentResponse;
import com.gotrash.api.v1.transformer.ShipmentTransformer;
import com.gotrash.service.ShipmentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
@Tag(name = "Shipment API", description = "API for Shipment")
public class ShipmentAPI {

  private final ShipmentService shipmentService;

  @PostMapping("/shipments")
  @Operation(summary = "API to create a new shipment")
  public ApiResponse<ShipmentResponse> save(@RequestBody ShipmentRequest shipmentRequest) {
    Shipment shipment = shipmentService.save(ShipmentTransformer.transformRequestToModel(shipmentRequest));
    ShipmentResponse shipmentResponse = ShipmentTransformer.transformModelToResponse(shipment);
    return new ApiResponse<>(HttpStatus.CREATED.value(), shipmentResponse);
  }

  @GetMapping("/shipments")
  @Operation(summary = "API to get all shipment data")
  public ApiResponse<List<ShipmentResponse>> getShipments() {
    List<Shipment> shipments = shipmentService.getShipments();
    List<ShipmentResponse> shipmentRespons = shipments.stream()
        .map(ShipmentTransformer::transformModelToResponse)
        .toList();
    return new ApiResponse<>(HttpStatus.OK.value(), shipmentRespons);
  }

  @GetMapping("/shipments/{shipment_id}")
  @Operation(summary = "API to get shipment by shipment id")
  public ApiResponse<ShipmentResponse> getShipmentByShipmentId(@PathVariable("shipment_id") String shipmentId) {
    ShipmentResponse shipmentResponse = ShipmentTransformer.transformModelToResponse(shipmentService.getShipmentById(shipmentId));
    return new ApiResponse<>(HttpStatus.OK.value(), shipmentResponse);
  }

  @GetMapping("/shipments/users/{user_id}")
  @Operation(summary = "API to get shipments by user id")
  public ApiResponse<List<ShipmentResponse>> getShipmentByUserId(@PathVariable("user_id") String userId) {

    List<Shipment> shipments = shipmentService.getShipmentByUserId(userId);
    List<ShipmentResponse> shipmentResponses = shipments.stream()
        .map(ShipmentTransformer::transformModelToResponse)
        .toList();

    return new ApiResponse<>(HttpStatus.OK.value(), shipmentResponses);
  }

  @PatchMapping("/shipments/{shipment_id}")
  @Operation(summary = "API to update shipment by shipment id")
  public ApiResponse<ShipmentResponse> updateShipment(@PathVariable("shipment_id") String shipmentId,
                                                      @RequestBody ShipmentRequest shipmentRequest) {
    Shipment shipment = shipmentService.update(ShipmentTransformer.transformRequestToModel(shipmentId, shipmentRequest));
    ShipmentResponse shipmentResponse = ShipmentTransformer.transformModelToResponse(shipment);
    return new ApiResponse<>(HttpStatus.OK.value(), shipmentResponse);
  }

  @DeleteMapping("/shipments/{shipment_id}")
  @Operation(summary = "API to delete shipment by shipment id")
  public ApiResponse<MessageResponse> deleteByShipmentId(@PathVariable("shipment_id") String shipmentId) {
    shipmentService.delete(shipmentId);
    String message = "Successfully Delete Shipment With ID " + shipmentId;
    MessageResponse messageResponse = new MessageResponse(message);

    return new ApiResponse<>(HttpStatus.OK.value(), messageResponse);
  }
}
