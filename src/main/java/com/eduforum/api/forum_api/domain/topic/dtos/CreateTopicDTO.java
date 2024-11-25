package com.eduforum.api.forum_api.domain.topic.dtos;

import jakarta.validation.constraints.NotNull;

public record CreateTopicDTO(
    @NotNull
    String title,
    @NotNull
    String content,
    @NotNull
    Long idCourse
) {
}
