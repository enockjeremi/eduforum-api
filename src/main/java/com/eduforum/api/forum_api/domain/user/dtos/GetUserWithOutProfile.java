package com.eduforum.api.forum_api.domain.user.dtos;

import com.eduforum.api.forum_api.domain.user.model.User;

public record GetUserWithOutProfile(
    Long id,
    String email
) {
  public GetUserWithOutProfile(User user){
    this(user.getId(), user.getEmail());
  }
}
