package com.eduforum.api.forum_api.infra.errors;

public class ValidateException extends RuntimeException {
  public ValidateException(String msg) {
    super(msg);
  }
}
