package com.gotrash.api.v1.transformer.trashhistory;

import com.gotrash.api.v1.model.Citizen;
import com.gotrash.api.v1.model.trashhistory.TrashHistory;
import com.gotrash.api.v1.model.trashhistory.TrashHistoryWasteBank;
import com.gotrash.api.v1.response.trashhistory.TrashHistoryWasteBankResponse;
import com.gotrash.api.v1.transformer.CitizenTransformer;
import com.gotrash.api.v1.transformer.TrashBinTransformer;
import com.gotrash.api.v1.transformer.TrashTransformer;
import com.gotrash.util.CalculatorUtil;

public class TrashHistoryWasteBankTransformer {

  public static TrashHistoryWasteBank transformToModel(TrashHistory trashHistory, Citizen citizen) {
    return TrashHistoryWasteBank.builder()
        .trashHistoryId(trashHistory.getTrashHistoryId())
        .citizen(citizen)
        .trash(trashHistory.getTrash())
        .trashBin(trashHistory.getTrashBin())
        .weight(trashHistory.getWeight())
        .totalCoin(
            CalculatorUtil.calculateCoin(trashHistory.getWeight(), trashHistory.getTrash().getCoin())
        )
        .createdAt(trashHistory.getCreatedAt())
        .updatedAt(trashHistory.getUpdatedAt())
        .build();
  }

  public static TrashHistoryWasteBankResponse transformModelToResponse(TrashHistoryWasteBank trashHistoryWasteBank) {
    return TrashHistoryWasteBankResponse.builder()
        .trashHistoryId(trashHistoryWasteBank.getTrashHistoryId())
        .citizen(CitizenTransformer.transformModelToResponse(trashHistoryWasteBank.getCitizen()))
        .trash(TrashTransformer.transformModelToResponse(trashHistoryWasteBank.getTrash()))
        .trashBin(TrashBinTransformer.transformModelToResponse(trashHistoryWasteBank.getTrashBin()))
        .weight(trashHistoryWasteBank.getWeight())
        .totalCoin(trashHistoryWasteBank.getTotalCoin())
        .createdAt(trashHistoryWasteBank.getCreatedAt())
        .updatedAt(trashHistoryWasteBank.getUpdatedAt())
        .build();
  }
}
