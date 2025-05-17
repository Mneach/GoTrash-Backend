package com.gotrash.api.v1.transformer.trashhistory;

import com.gotrash.api.v1.model.trashhistory.TrashHistoryIoT;
import com.gotrash.api.v1.model.trashhistory.TrashHistoryManual;
import com.gotrash.api.v1.request.trashhistory.TrashHistoryIoTRequest;
import com.gotrash.api.v1.request.trashhistory.TrashHistoryManualRequest;

import java.math.BigDecimal;
import java.math.BigInteger;

public class TrashHistoryIoTTransformer {
  public static TrashHistoryIoT transformRequestToModel(TrashHistoryIoTRequest trashHistoryIoTRequest) {
    return TrashHistoryIoT.builder()
        .trashName(trashHistoryIoTRequest.getTrashName())
        .trashBinId(trashHistoryIoTRequest.getTrashBinId())
        .bleId(trashHistoryIoTRequest.getBleId())
        .weight(trashHistoryIoTRequest.getWeight())
        .build();
  }

}
