package com.eduforum.api.forum_api.infra.errors;

public class ForbiddenException extends RuntimeException {
  public ForbiddenException(String msg) {
    super(msg);
  }
}
