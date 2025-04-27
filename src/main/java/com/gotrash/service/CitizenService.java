package com.gotrash.service;

import com.gotrash.api.v1.model.Citizen;
import com.gotrash.api.v1.model.User;
import com.gotrash.api.v1.model.TrashHistory;
import com.gotrash.api.v1.model.Group;
import com.gotrash.api.v1.transformer.CitizenTransformer;
import com.gotrash.api.v1.transformer.UserTransformer;
import com.gotrash.entity.CitizenEntity;
import com.gotrash.entity.GovernmentEntity;
import com.gotrash.entity.UserEntity;
import com.gotrash.repository.CitizenRepository;
import com.gotrash.repository.UserRepository;
import com.gotrash.util.AuthorityUtil;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.time.LocalDateTime;

@Service
@AllArgsConstructor
public class CitizenService {
  private final CitizenRepository citizenRepository;
  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;
  private final TrashHistoryService trashHistoryService;
  private final GroupService groupService;

  @Transactional
  public Citizen save(Citizen citizen) {

    User user = User.builder()
        .email(citizen.getEmail())
        .role(citizen.getRole())
        .password(passwordEncoder.encode(citizen.getPassword()))
        .build();

    UserEntity userEntity = userRepository.save(UserTransformer.transformModelToEntity(user));

    CitizenEntity citizenEntity = CitizenTransformer.transformModelToEntity(citizen);
    citizenEntity.setUser(userEntity);

    return CitizenTransformer.transformEntityToModel(
        citizenRepository.save(citizenEntity));
  }

  public List<Citizen> getCitizens() {
    List<CitizenEntity> citizenEntities = citizenRepository.findAll();

    return citizenEntities.stream()
        .map(CitizenTransformer::transformEntityToModel)
        .toList();
  }

  public Citizen getCitizenByUserId(String userId) {
    Optional<CitizenEntity> citizenEntityOptional = citizenRepository.findByUser_UserId(UUID.fromString(userId));

    if (citizenEntityOptional.isEmpty()) {
      throw new EntityNotFoundException("Citizen with User ID " + userId + " Not Found");
    }

    return CitizenTransformer.transformEntityToModel(citizenEntityOptional.get());
  }

  public Citizen getMe() {
    String userId = AuthorityUtil.getCurrentUserId();

    List<TrashHistory> trashHistories = trashHistoryService.getTrashHistoryByUserId(userId);
    Citizen citizen = getCitizenByUserId(userId);

    List<Group> groups = groupService.getGroupsFilterByUserId(userId);

    citizen.setTrashHistories(trashHistories);
    citizen.setGroups(groups);

    return citizen;
  }

  @Transactional
  public Citizen update(Citizen citizen) {

    CitizenEntity citizenEntity = citizenRepository.findById(UUID.fromString(citizen.getUserId()))
        .orElseThrow(() -> new EntityNotFoundException("Citizen with ID " + citizen.getUserId() + " not found"));

    UserEntity userEntity = userRepository.findById(UUID.fromString(citizen.getUserId()))
        .orElseThrow(() -> new EntityNotFoundException("User with ID " + citizen.getUserId() + " not found"));

    userEntity.setEmail(citizen.getEmail());
    userEntity.setRole(citizen.getRole());
    userEntity.setPassword(passwordEncoder.encode(citizen.getPassword()));
    userEntity = userRepository.save(userEntity);

    citizenEntity.setUser(userEntity);
    citizenEntity.setName(citizen.getName());
    citizenEntity.setPhoneNumber(citizen.getPhoneNumber());
    citizenEntity.setImageName(citizen.getImageName());
    citizenEntity.setImageUrl(citizen.getImageUrl());
    citizenEntity.setCoin(citizen.getCoin());
    citizenEntity.setUpdatedAt(LocalDateTime.now());

    return CitizenTransformer.transformEntityToModel(
        citizenRepository.save(citizenEntity));
  }

  @Transactional
  public void delete(String citizenId) {
    if (!citizenRepository.existsById(UUID.fromString(citizenId))) {
      throw new EntityNotFoundException("Citizen with ID " + citizenId + " Not Found");
    }

    citizenRepository.deleteById(UUID.fromString(citizenId));
  }

}
