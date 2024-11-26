package com.eduforum.api.forum_api.domain.user.dtos;

public record GetToken(
    Boolean success,
    String accessToken
) {
}
