package com.eduforum.api.forum_api.domain.course.dtos;

import com.eduforum.api.forum_api.domain.course.model.Course;

public record GetCourse(
    Long id,
    String name,
    String category
) {
  public GetCourse(Course course) {
    this(course.getId(), course.getName(), course.getCategory());
  }
}
