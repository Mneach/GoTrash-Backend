package com.gotrash.api.v1.transformer;

import com.gotrash.api.v1.model.Exchange;
import com.gotrash.api.v1.model.Reward;
import com.gotrash.api.v1.model.User;
import com.gotrash.api.v1.request.ExchangeRequest;
import com.gotrash.api.v1.response.ExchangeResponse;
import com.gotrash.entity.ExchangeEntity;

import java.util.UUID;

public class ExchangeTransformer {

  public static Exchange transformRequestToModel(String exchangeId, ExchangeRequest exchangeRequest) {
    return Exchange.builder()
        .exchangeId(exchangeId)
        .user(User.builder().userId(exchangeRequest.getUserId()).build())
        .reward(Reward.builder().rewardId(exchangeRequest.getRewardId()).build())
        .status(exchangeRequest.getStatus())
        .description(exchangeRequest.getDescription())
        .build();
  }

  public static Exchange transformRequestToModel(ExchangeRequest exchangeRequest) {
    return Exchange.builder()
        .user(User.builder().userId(exchangeRequest.getUserId()).build())
        .reward(Reward.builder().rewardId(exchangeRequest.getRewardId()).build())
        .status(exchangeRequest.getStatus())
        .description(exchangeRequest.getDescription())
        .build();
  }

  public static Exchange transformEntityToModel(ExchangeEntity exchangeEntity) {
    return Exchange.builder()
        .exchangeId(exchangeEntity.getExchangeId().toString())
        .user(UserTransformer.transformEntityToModel(exchangeEntity.getUser()))
        .reward(RewardTransformer.transformEntityToModel(exchangeEntity.getReward()))
        .description(exchangeEntity.getDescription())
        .status(exchangeEntity.getStatus())
        .createdAt(exchangeEntity.getCreatedAt())
        .updatedAt(exchangeEntity.getUpdatedAt())
        .build();
  }

  public static ExchangeEntity transformModelToEntity(Exchange exchange) {
    return ExchangeEntity.builder()
        .exchangeId(exchange.getExchangeId() != null ? UUID.fromString(exchange.getExchangeId()) : null)
        .user(UserTransformer.transformModelToEntity(exchange.getUser()))
        .reward(RewardTransformer.transformModelToEntity(exchange.getReward()))
        .description(exchange.getDescription())
        .status(exchange.getStatus())
        .createdAt(exchange.getCreatedAt())
        .updatedAt(exchange.getUpdatedAt())
        .build();
  }

  public static ExchangeResponse transformModelToResponse(Exchange exchange) {
    return ExchangeResponse.builder()
        .exchangeId(exchange.getExchangeId())
        .user(UserTransformer.transformModelToResponse(exchange.getUser()))
        .reward(RewardTransformer.transformModelToResponse(exchange.getReward()))
        .description(exchange.getDescription())
        .status(exchange.getStatus())
        .createdAt(exchange.getCreatedAt())
        .updatedAt(exchange.getUpdatedAt())
        .build();
  }
}
