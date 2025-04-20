package com.gotrash.exception.rest;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class ForbiddenAccessException extends ResponseStatusException {
  public ForbiddenAccessException(String reason) {
    super(HttpStatus.FORBIDDEN, reason);
  }
}
