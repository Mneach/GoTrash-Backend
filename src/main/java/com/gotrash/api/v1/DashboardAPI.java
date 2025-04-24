package com.gotrash.api.v1;

import com.gotrash.api.v1.model.dashboard.WasteBankTrashCategorySummary;
import com.gotrash.api.v1.model.dashboard.WasteBankTrashSummary;
import com.gotrash.service.DashboardService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
@Tag(name = "Dashboard API", description = "API for gotrash dashboard")
public class DashboardAPI {

  private final DashboardService dashboardService;

  @GetMapping("/dashboards/waste-banks/{waste_bank_id}/total-trash")
  @Operation(summary = "Get total trash by waste_bank_id")
  public ResponseEntity<WasteBankTrashSummary> getTotalTrashByWasteBankId(@PathVariable("waste_bank_id") String wasteBankId) {
    WasteBankTrashSummary wasteBankTrashSummary = dashboardService.getTotalTrashByWasteBankId(wasteBankId);
    return new ResponseEntity<>(wasteBankTrashSummary, HttpStatus.OK);
  }

  @GetMapping("/dashboards/waste-banks/total-trash")
  @Operation(summary = "Get total trash group by waste bank")
  public ResponseEntity<List<WasteBankTrashSummary>> getTotalTrashGroupByWasteBank() {
    List<WasteBankTrashSummary> wasteBankTrashSummaries = dashboardService.getTotalTrashGroupByWasteBank();
    return new ResponseEntity<>(wasteBankTrashSummaries, HttpStatus.OK);
  }

  @GetMapping("/dashboards/waste-banks/{waste_bank_id}/total-trash-by-category")
  @Operation(summary = "Get total trash by waste_bank_id group by trash category")
  public ResponseEntity<List<WasteBankTrashCategorySummary>> getTotalTrashByWasteBankIdGroupByTrashCategory(
      @PathVariable("waste_bank_id") String wasteBankId
  ) {
    List<WasteBankTrashCategorySummary> wasteBankTrashCategorySummaries = dashboardService.getTotalTrashByWasteBankIdGroupByTrashCategory(
        wasteBankId
    );

    return new ResponseEntity<>(wasteBankTrashCategorySummaries, HttpStatus.OK);
  }

}
