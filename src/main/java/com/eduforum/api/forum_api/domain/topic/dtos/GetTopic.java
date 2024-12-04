package com.eduforum.api.forum_api.domain.topic.dtos;

import com.eduforum.api.forum_api.domain.course.dtos.GetCourse;
import com.eduforum.api.forum_api.domain.topic.model.Topic;
import com.eduforum.api.forum_api.domain.user.dtos.GetUserWithProfile;

import java.time.Instant;

public record GetTopic(
    Long id,
    String title,
    String content,
    Boolean status,
    Integer answersTotal,
    GetCourse course,
    GetUserWithProfile author,
    Instant updatedOn
) {
  public GetTopic(Topic topic) {
    this(topic.getId(),
        topic.getTitle(),
        topic.getContent(),
        topic.getStatus(),
        topic.getAnswers() == null ? 0 : topic.getAnswers().size(),
        new GetCourse(topic.getCourse()),
        new GetUserWithProfile(topic.getUser()),
        topic.getLastUpdatedOn());
  }
}
