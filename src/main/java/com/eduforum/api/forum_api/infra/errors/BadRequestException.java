package com.eduforum.api.forum_api.infra.errors;

public class BadRequestException extends RuntimeException {
  public BadRequestException(String msg) {
    super(msg);
  }
}
