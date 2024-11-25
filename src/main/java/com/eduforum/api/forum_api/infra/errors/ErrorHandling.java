package com.eduforum.api.forum_api.infra.errors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.OffsetDateTime;

@RestControllerAdvice
public class ErrorHandling {
  private OffsetDateTime now;

  @ExceptionHandler(ValidateException.class)
  public ResponseEntity<ResponseException> errorValidateID(ValidateException e) {
    now = OffsetDateTime.now();
    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
        new ResponseException(now, false, HttpStatus.NOT_FOUND.value(), "Not Found", e.getMessage()));
  }

  public record ResponseException(
      OffsetDateTime timestamp,
      Boolean success,
      Integer status,
      String error,
      String message
  ) {
  }
}
