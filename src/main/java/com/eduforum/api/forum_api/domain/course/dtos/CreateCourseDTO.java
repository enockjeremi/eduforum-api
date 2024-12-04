package com.eduforum.api.forum_api.domain.course.dtos;

import com.eduforum.api.forum_api.domain.course.model.Categories;
import jakarta.validation.constraints.NotNull;

public record CreateCourseDTO(
    @NotNull
    String name,
    @NotNull
    Categories category
) {

}
