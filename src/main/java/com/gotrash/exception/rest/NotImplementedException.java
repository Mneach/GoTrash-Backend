package com.gotrash.exception.rest;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class NotImplementedException extends ResponseStatusException {

  public NotImplementedException(String reason) {
    super(HttpStatus.NOT_IMPLEMENTED, reason);
  }

  public NotImplementedException(String reason, Throwable cause) {
    super(HttpStatus.NOT_IMPLEMENTED, reason, cause);
  }
}
