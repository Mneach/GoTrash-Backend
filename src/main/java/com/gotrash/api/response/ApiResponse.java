package com.gotrash.api.response;

import lombok.Data;
import org.springframework.http.HttpStatusCode;

import java.time.Instant;
import java.time.LocalDateTime;

@Data
public class ApiResponse<T> {

  private int status;
  private String message;
  private String timestamp;
  private T data;

  public ApiResponse(int status, T data) {
    this.status = status;
    this.message = "Success";
    this.timestamp = Instant.now().toString();
    this.data = data;
  }
  
  public ApiResponse(int status, String message, T data) {
    this.status = status;
    this.message = message;
    this.timestamp = Instant.now().toString();
    this.data = data;
  }

  public ApiResponse(int status, String message) {
    this.status = status;
    this.message = message;
    this.timestamp = Instant.now().toString();
    this.data = null;
  }
}
