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
import org.springframework.web.bind.annotation.RequestParam;
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
    Shipment shipment = ShipmentTransformer.transformRequestToModel(shipmentRequest);
    ShipmentResponse shipmentResponse = ShipmentTransformer.transformModelToResponse(shipmentService.save(shipment));
    return new ApiResponse<>(HttpStatus.CREATED.value(), shipmentResponse);
  }

  @GetMapping("/shipments")
  @Operation(summary = "API to get all shipment data")
  public ApiResponse<List<ShipmentResponse>> getShipments(
      @RequestParam(name = "companyId", required = false) String companyId,
      @RequestParam(name = "wasteBankId", required = false) String wasteBankId
  ) {
    List<Shipment> shipments;

    if (companyId != null) {
      shipments = shipmentService.getShipmentFilterByCompanyId(companyId);
    } else if (wasteBankId != null) {
      shipments = shipmentService.getShipmentFilterByWasteBankId(wasteBankId);
    } else {
      shipments = shipmentService.getShipments();
    }

    List<ShipmentResponse> shipmentResponses = shipments.stream()
        .map(ShipmentTransformer::transformModelToResponse)
        .toList();
    return new ApiResponse<>(HttpStatus.OK.value(), shipmentResponses);
  }

  @GetMapping("/shipments/{shipment_id}")
  @Operation(summary = "API to get shipment by shipment id")
  public ApiResponse<ShipmentResponse> getShipmentByShipmentId(@PathVariable("shipment_id") String shipmentId) {
    ShipmentResponse shipmentResponse = ShipmentTransformer.transformModelToResponse(
        shipmentService.getShipmentByShipmentId(shipmentId)
    );
    return new ApiResponse<>(HttpStatus.OK.value(), shipmentResponse);
  }

  @PatchMapping("/shipments/{shipment_id}")
  @Operation(summary = "API to update shipment by shipment id")
  public ApiResponse<ShipmentResponse> update(@PathVariable("shipment_id") String shipmentId,
                                              @RequestBody ShipmentRequest shipmentRequest) {
    Shipment shipment = ShipmentTransformer.transformRequestToModel(shipmentId, shipmentRequest);
    ShipmentResponse shipmentResponse = ShipmentTransformer.transformModelToResponse(shipmentService.update(shipment));
    return new ApiResponse<>(HttpStatus.OK.value(), shipmentResponse);
  }

  @PatchMapping("/shipments/{shipment_id}/status/done")
  @Operation(summary = "API to update shipment status to done")
  public ApiResponse<ShipmentResponse> updateShipmentStatusToDone(@PathVariable("shipment_id") String shipmentId) {
    ShipmentResponse shipmentResponse = ShipmentTransformer.transformModelToResponse(
        shipmentService.updateShipmentStatusToDone(shipmentId)
    );
    return new ApiResponse<>(HttpStatus.OK.value(), shipmentResponse);
  }

  @DeleteMapping("/shipments/{shipment_id}")
  @Operation(summary = "API to delete shipment by shipment id")
  public ApiResponse<MessageResponse> delete(@PathVariable("shipment_id") String shipmentId) {
    shipmentService.delete(shipmentId);
    String message = "Successfully delete trash category with id " + shipmentId;
    return new ApiResponse<>(HttpStatus.OK.value(), message);
  }
}
