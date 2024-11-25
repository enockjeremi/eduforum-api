package com.eduforum.api.forum_api.domain.answer.dtos;

import jakarta.validation.constraints.NotNull;

public record CreateAnswerDTO (
    @NotNull
    String message,

    @NotNull
    Long idTopic
) {
}
