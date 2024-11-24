package com.eduforum.api.forum_api.domain.course.dtos;

import jakarta.validation.constraints.NotNull;

public record CreateCourseDTO(
    @NotNull
    String name,
    @NotNull
    String category
) {

}
