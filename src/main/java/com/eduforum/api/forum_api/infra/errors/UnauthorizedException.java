package com.eduforum.api.forum_api.infra.errors;

public class UnauthorizedException extends RuntimeException {
  public UnauthorizedException(String msg) {
    super(msg);
  }
}
