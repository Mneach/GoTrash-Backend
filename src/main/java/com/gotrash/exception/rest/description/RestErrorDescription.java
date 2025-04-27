package com.gotrash.exception.rest.description;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.server.ResponseStatusException;

import java.time.Instant;
import java.util.Map;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class RestErrorDescription {
  private String timestamp;
  private int status;
  private String message;
  private Object data;

  public RestErrorDescription(ResponseStatusException exception, WebRequest webRequest) {
    this(
        Instant.now().toString(),
        exception.getStatusCode().value(),
        exception.getReason(),
        null
    );
  }
}
