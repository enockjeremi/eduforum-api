package com.eduforum.api.forum_api.domain.topic.dtos;

import com.eduforum.api.forum_api.domain.course.dtos.GetCourse;
import com.eduforum.api.forum_api.domain.topic.model.Topic;

import java.time.Instant;

public record GetAllTopic(
    Long id,
    String title,
    Boolean status,
    GetCourse course,
    Instant createdOn
) {
  public GetAllTopic(Topic topic) {
    this(topic.getId(),
        topic.getTitle(),
        topic.getStatus(),
        new GetCourse(topic.getCourse()),
        topic.getCreatedOn());
  }
}
