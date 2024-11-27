package com.eduforum.api.forum_api.domain.topic.dtos;

import com.eduforum.api.forum_api.domain.answer.model.Answer;
import com.eduforum.api.forum_api.domain.course.dtos.GetCourse;
import com.eduforum.api.forum_api.domain.topic.model.Topic;
import com.eduforum.api.forum_api.domain.user.dtos.GetUser;

import java.time.Instant;
import java.util.List;

public record GetTopic(
    Long id,
    String title,
    String content,
    Boolean status,
    GetCourse course,
    GetUser author,
    Instant createdOn
) {
  public GetTopic(Topic topic) {
    this(topic.getId(),
        topic.getTitle(),
        topic.getContent(),
        topic.getStatus(),
        new GetCourse(topic.getCourse()),
        new GetUser(topic.getUser()),
        topic.getCreatedOn());
  }
}
