package com.gotrash.exception.rest;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class InternalServerException extends ResponseStatusException {
  public InternalServerException(String reason) {
    super(HttpStatus.INTERNAL_SERVER_ERROR, reason);
  }

  public InternalServerException(String reason, Throwable cause) {
    super(HttpStatus.INTERNAL_SERVER_ERROR, reason, cause);
  }
}
