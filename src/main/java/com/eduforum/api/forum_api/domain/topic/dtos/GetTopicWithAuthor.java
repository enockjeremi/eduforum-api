package com.eduforum.api.forum_api.domain.topic.dtos;

import com.eduforum.api.forum_api.domain.course.dtos.GetCourse;
import com.eduforum.api.forum_api.domain.topic.model.Topic;
import com.eduforum.api.forum_api.domain.user.dtos.GetUserWithProfile;

import java.time.Instant;

public record GetTopicWithAuthor(
    Long id,
    String title,
    String content,
    Boolean status,
    GetCourse course,
    GetUserWithProfile author,
    Instant createdOn
) {
  public GetTopicWithAuthor(Topic topic) {
    this(topic.getId(),
        topic.getTitle(),
        topic.getContent(),
        topic.getStatus(),
        new GetCourse(topic.getCourse()),
        new GetUserWithProfile(topic.getUser()),
        topic.getCreatedOn());
  }
}
