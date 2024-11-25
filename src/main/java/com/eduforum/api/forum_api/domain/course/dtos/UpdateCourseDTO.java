package com.eduforum.api.forum_api.domain.course.dtos;

import jakarta.validation.constraints.NotNull;

public record UpdateCourseDTO(
    String name,
    String category
) {

}
