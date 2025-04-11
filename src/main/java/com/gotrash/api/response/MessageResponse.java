package com.gotrash.api.response;

import lombok.Data;

@Data
public class MessageResponse {

  private static final String DEFAULT_MESSAGE = "Request has been successfully completed";
  private final String message;

  public MessageResponse() {
    this.message = DEFAULT_MESSAGE;
  }

  public MessageResponse(String message) {
    this.message = message;
  }
}
