package com.eduforum.api.forum_api.domain.user.dtos;

import jakarta.validation.constraints.NotNull;

public record CreateProfileDTO(
    @NotNull
    String name,
    @NotNull
    String lastName,
    @NotNull
    String country
) {
}
