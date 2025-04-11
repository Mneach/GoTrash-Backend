package com.gotrash.service;

import com.gotrash.api.v1.model.Exchange;
import com.gotrash.api.v1.model.Reward;
import com.gotrash.api.v1.model.User;
import com.gotrash.api.v1.transformer.ExchangeTransformer;
import com.gotrash.api.v1.transformer.RewardTransformer;
import com.gotrash.api.v1.transformer.UserTransformer;
import com.gotrash.entity.ExchangeEntity;
import com.gotrash.repository.ExchangeRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ExchangeService {

  private final ExchangeRepository exchangeRepository;
  private final RewardService rewardService;
  private final UserService userService;

  public Exchange save(Exchange exchange) {

    Reward reward = rewardService.getRewardByRewardId(exchange.getReward().getRewardId());
    User user = userService.getUserByUserId(exchange.getUser().getUserId());

    exchange.setReward(reward);
    exchange.setUser(user);

    ExchangeEntity exchangeEntity = exchangeRepository.save(ExchangeTransformer.transformModelToEntity(exchange));
    return ExchangeTransformer.transformEntityToModel(exchangeEntity);
  }

  public Exchange getExchangeById(String exchangeId) {
    Optional<ExchangeEntity> exchangeEntityOptional = exchangeRepository.findById(UUID.fromString(exchangeId));

    if (exchangeEntityOptional.isEmpty()) {
      throw new EntityNotFoundException("Exchange With ID " + exchangeId + " Not Found");
    }

    return ExchangeTransformer.transformEntityToModel(exchangeEntityOptional.get());
  }

  public List<Exchange> getExchangeByUserId(String userId) {
    List<ExchangeEntity> exchangeEntities = exchangeRepository.findAllByUser_UserId(UUID.fromString(userId));

    return exchangeEntities.stream()
        .map(ExchangeTransformer::transformEntityToModel)
        .toList();
  }

  public Exchange update(Exchange exchange) {
    Optional<ExchangeEntity> exchangeEntityOptional = exchangeRepository.findById(UUID.fromString(exchange.getExchangeId()));

    if (exchangeEntityOptional.isEmpty()) {
      throw new EntityNotFoundException("Exchange With ID " + exchange.getExchangeId() + " Not Found");
    }

    ExchangeEntity exchangeEntity = exchangeEntityOptional.get();

    if (exchange.getReward() != null && exchange.getReward().getRewardId() != null) {
      Reward reward = rewardService.getRewardByRewardId(exchange.getReward().getRewardId());
      exchangeEntity.setReward(RewardTransformer.transformModelToEntity(reward));
    }

    if (exchange.getUser() != null && exchange.getUser().getUserId() != null) {
      User user = userService.getUserByUserId(exchange.getUser().getUserId());
      exchangeEntity.setUser(UserTransformer.transformModelToEntity(user));
    }

    exchangeEntity.setStatus(exchange.getStatus());
    exchangeEntity.setDescription(exchange.getDescription());

    exchangeEntity = exchangeRepository.save(exchangeEntity);
    return ExchangeTransformer.transformEntityToModel(exchangeEntity);
  }

  public void delete(String exchangeId) {
    if (!exchangeRepository.existsById(UUID.fromString(exchangeId))) {
      throw new EntityNotFoundException("Exchange With ID " + exchangeId + " Not Found");
    }

    exchangeRepository.deleteById(UUID.fromString(exchangeId));
  }
}
