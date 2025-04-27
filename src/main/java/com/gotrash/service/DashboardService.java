package com.gotrash.service;

import com.gotrash.api.v1.model.dashboard.WasteBankTrashCategorySummary;
import com.gotrash.api.v1.model.dashboard.WasteBankTrashSummary;
import com.gotrash.repository.WasteBankRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DashboardService {

  private final WasteBankRepository wasteBankRepository;

  public WasteBankTrashSummary getTotalTrashByWasteBankId (String wasteBankId) {
    return wasteBankRepository.sumTrashWeightByWasteBankId(UUID.fromString(wasteBankId));
  }

  public List<WasteBankTrashSummary> getTotalTrashGroupByWasteBank() {
    return wasteBankRepository.sumTrashWeightByGroupByWasteBankId();
  }

  public List<WasteBankTrashCategorySummary> getTotalTrashByWasteBankIdGroupByTrashCategory(String wasteBankId) {
    return wasteBankRepository.sumTrashWeightByWasteBankIdGroupedByCategory(UUID.fromString(wasteBankId));
  }
}
