package com.eduforum.api.forum_api.infra.errors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.OffsetDateTime;
import java.util.List;

@RestControllerAdvice
public class ErrorHandling {
  private OffsetDateTime now;

  @ExceptionHandler(ValidateException.class)
  public ResponseEntity<ResponseException> errorValidateID(ValidateException e) {
    now = OffsetDateTime.now();
    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
        new ResponseException(now, false, HttpStatus.NOT_FOUND.value(), "Not Found", e.getMessage()));
  }

  @ExceptionHandler(UnauthorizedException.class)
  public ResponseEntity<ResponseException> errorUnauthorized(UnauthorizedException e) {
    now = OffsetDateTime.now();
    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(
        new ResponseException(now, false, HttpStatus.UNAUTHORIZED.value(), "Unauthorized", e.getMessage()));
  }

  @ExceptionHandler(ForbiddenException.class)
  public ResponseEntity<ResponseException> errorForbidden(ForbiddenException e) {
    now = OffsetDateTime.now();
    return ResponseEntity.status(HttpStatus.FORBIDDEN).body(
        new ResponseException(now, false, HttpStatus.FORBIDDEN.value(), "Forbidden", e.getMessage()));
  }

  @ExceptionHandler(BadRequestException.class)
  public ResponseEntity<ResponseException> errorBadRequest(BadRequestException e) {
    now = OffsetDateTime.now();
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
        new ResponseException(now, false, HttpStatus.BAD_REQUEST.value(), "Bad Request", e.getMessage()));
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<ResponseBadRequestException> errorBadRequest(MethodArgumentNotValidException e) {
    now = OffsetDateTime.now();
    var error = e.getFieldErrors().stream().map(ConvertFieldsBadRequest::new).toList();
    return ResponseEntity.badRequest().body(
        new ResponseBadRequestException(
            now,
            false,
            HttpStatus.BAD_REQUEST.value(),
            "Bad Request",
            error
        ));
  }

  public record ResponseBadRequestException(
      OffsetDateTime timestamp,
      Boolean success,
      Integer status,
      String error,
      List<ConvertFieldsBadRequest> messages
  ) {
  }

  private record ConvertFieldsBadRequest(String field, String error) {
    public ConvertFieldsBadRequest(FieldError error) {
      this(error.getField(), error.getDefaultMessage());
    }
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
