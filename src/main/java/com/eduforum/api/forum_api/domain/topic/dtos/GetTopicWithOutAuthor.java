package com.eduforum.api.forum_api.domain.topic.dtos;

import com.eduforum.api.forum_api.domain.answer.dtos.GetAnswer;
import com.eduforum.api.forum_api.domain.answer.model.Answer;
import com.eduforum.api.forum_api.domain.course.dtos.GetCourse;
import com.eduforum.api.forum_api.domain.topic.model.Topic;

import java.time.Instant;

public record GetTopicWithOutAuthor(
    Long id,
    String title,
    Boolean status,
    GetAnswer solution,
    GetCourse course,
    Instant createdOn
) {
  public GetTopicWithOutAuthor(Topic topic) {

    this(topic.getId(),
        topic.getTitle(),
        topic.getStatus(),
        topic.getSolutionAnswer() != null ? new GetAnswer(topic.getSolutionAnswer()) : null,
        new GetCourse(topic.getCourse()),
        topic.getCreatedOn());
  }
}
