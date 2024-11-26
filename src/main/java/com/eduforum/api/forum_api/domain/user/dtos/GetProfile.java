package com.eduforum.api.forum_api.domain.user.dtos;

import com.eduforum.api.forum_api.domain.user.model.Profile;

public record GetProfile(
    String name,
    String lastName,
    String country
) {
  public GetProfile(Profile profile) {
    this(profile.getName(), profile.getLastName(), profile.getCountry());
  }
}
