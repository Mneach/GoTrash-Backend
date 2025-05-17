package com.gotrash.api.v1.response.trashhistory;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigInteger;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TrashHistoryIoTResponse {
  private BigInteger bleId;
}
