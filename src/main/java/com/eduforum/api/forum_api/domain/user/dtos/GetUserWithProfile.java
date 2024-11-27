package com.eduforum.api.forum_api.domain.user.dtos;

import com.eduforum.api.forum_api.domain.user.model.User;

public record GetUserWithProfile(
    Long id,
    String email,
    GetProfile profile
) {
  public GetUserWithProfile(User user){
    this(user.getId(), user.getEmail(), new GetProfile(user.getProfile()));
  }
}
