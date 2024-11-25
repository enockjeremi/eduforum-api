package com.eduforum.api.forum_api.domain.topic.dtos;

import com.eduforum.api.forum_api.domain.course.dtos.GetCourse;
import com.eduforum.api.forum_api.domain.topic.model.Topic;

import java.time.Instant;

public record GetTopic(
    Long id,
    String title,
    String content,
    GetCourse course,
    Boolean status,
    Instant createdOn
) {
  public GetTopic(Topic topic) {
    this(topic.getId(), topic.getTitle(), topic.getContent(), new GetCourse(topic.getCourse()), topic.getStatus(), topic.getCreatedOn());
  }
}
