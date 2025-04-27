package com.gotrash.api.v1;

import com.gotrash.api.response.ApiResponse;
import com.gotrash.api.response.MessageResponse;
import com.gotrash.api.v1.model.Exchange;
import com.gotrash.api.v1.request.ExchangeRequest;
import com.gotrash.api.v1.response.ExchangeResponse;
import com.gotrash.api.v1.transformer.ExchangeTransformer;
import com.gotrash.service.ExchangeService;
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
@Tag(name = "Exchange API", description = "API for exchange")
public class ExchangeAPI {

  private final ExchangeService exchangeService;

  @PostMapping("/exchanges")
  @Operation(summary = "API to create a new exchange")
  public ApiResponse<ExchangeResponse> save(@RequestBody ExchangeRequest exchangeRequest) {
    Exchange exchange = exchangeService.save(ExchangeTransformer.transformRequestToModel(exchangeRequest));
    ExchangeResponse exchangeResponse = ExchangeTransformer.transformModelToResponse(exchange);
    return new ApiResponse<>(HttpStatus.CREATED.value(), exchangeResponse);
  }

  @GetMapping("/exchanges")
  @Operation(summary = "API to get all exchange data")
  public ApiResponse<List<ExchangeResponse>> getExchanges() {
    List<Exchange> exchanges = exchangeService.getExchanges();
    List<ExchangeResponse> exchangeResponses = exchanges.stream()
        .map(ExchangeTransformer::transformModelToResponse)
        .toList();
    return new ApiResponse<>(HttpStatus.OK.value(), exchangeResponses);
  }

  @GetMapping("/exchanges/{exchange_id}")
  @Operation(summary = "API to get exchange by exchange id")
  public ApiResponse<ExchangeResponse> getExchangeByExchangeId(@PathVariable("exchange_id") String exchangeId) {
    ExchangeResponse exchangeResponse = ExchangeTransformer.transformModelToResponse(exchangeService.getExchangeById(exchangeId));
    return new ApiResponse<>(HttpStatus.OK.value(), exchangeResponse);
  }

  @GetMapping("/exchanges/users/{user_id}")
  @Operation(summary = "API to get exchanges by user id")
  public ApiResponse<List<ExchangeResponse>> getExchangeByUserId(@PathVariable("user_id") String userId) {

    List<Exchange> exchanges = exchangeService.getExchangeByUserId(userId);
    List<ExchangeResponse> exchangeResponses = exchanges.stream()
        .map(ExchangeTransformer::transformModelToResponse)
        .toList();

    return new ApiResponse<>(HttpStatus.OK.value(), exchangeResponses);
  }

  @PatchMapping("/exchanges/{exchange_id}")
  @Operation(summary = "API to update exchange by exchange id")
  public ApiResponse<ExchangeResponse> updateExchange(@PathVariable("exchange_id") String exchangeId,
                                                         @RequestBody ExchangeRequest exchangeRequest) {
    Exchange exchange = exchangeService.update(ExchangeTransformer.transformRequestToModel(exchangeId, exchangeRequest));
    ExchangeResponse exchangeResponse = ExchangeTransformer.transformModelToResponse(exchange);
    return new ApiResponse<>(HttpStatus.OK.value(), exchangeResponse);
  }

  @DeleteMapping("/exchanges/{exchange_id}")
  @Operation(summary = "API to delete exchange by exchange id")
  public ApiResponse<MessageResponse> deleteByExchangeId(@PathVariable("exchange_id") String exchangeId) {
    exchangeService.delete(exchangeId);
    String message = "Successfully Delete Exchange With ID " + exchangeId;
    MessageResponse messageResponse = new MessageResponse(message);

    return new ApiResponse<>(HttpStatus.OK.value(), messageResponse);
  }
}
