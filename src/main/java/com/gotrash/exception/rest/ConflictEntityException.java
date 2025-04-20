package com.gotrash.exception.rest;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class ConflictEntityException extends ResponseStatusException {
  public ConflictEntityException(String reason) {
    super(HttpStatus.CONFLICT, reason);
  }
}
