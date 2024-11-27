package com.eduforum.api.forum_api.domain.user.dtos;

import com.eduforum.api.forum_api.domain.user.model.User;

public record GetUser(
    Long id,
    String email,
    GetProfile profile
) {
  public GetUser(User user){
    this(user.getId(), user.getEmail(), new GetProfile(user.getProfile()));
  }
}
