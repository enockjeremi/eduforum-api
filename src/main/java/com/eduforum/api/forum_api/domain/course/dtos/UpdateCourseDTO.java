package com.eduforum.api.forum_api.domain.course.dtos;

import com.eduforum.api.forum_api.domain.course.model.Categories;

public record UpdateCourseDTO(
    String name,
    Categories category
) {

}
