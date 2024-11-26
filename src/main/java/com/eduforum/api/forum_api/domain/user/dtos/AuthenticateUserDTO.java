package com.eduforum.api.forum_api.domain.user.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;

public record AuthenticateUserDTO(
    @NotNull
    @Email
    String email,

    @NotNull
    String password
) {
}
