package com.gotrash.api.v1.transformer;

import com.gotrash.api.v1.model.TrashHistoryManual;
import com.gotrash.api.v1.request.TrashHistoryManualRequest;

public class TrashHistoryManualTransformer {

  public static TrashHistoryManual transformRequestToModel(TrashHistoryManualRequest trashHistoryManualRequest) {
    return TrashHistoryManual.builder()
        .trashId(trashHistoryManualRequest.getTrashId())
        .trashBinId(trashHistoryManualRequest.getTrashBinId())
        .phoneNumber(trashHistoryManualRequest.getPhoneNumber())
        .weight(trashHistoryManualRequest.getWeight())
        .build();
  }
}
