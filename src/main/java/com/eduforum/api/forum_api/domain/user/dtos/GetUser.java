package com.eduforum.api.forum_api.domain.user.dtos;

public record GetUser(
    Long id,
    String email,
    GetProfile profile
) {
}
