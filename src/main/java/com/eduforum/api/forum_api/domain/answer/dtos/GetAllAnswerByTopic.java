package com.eduforum.api.forum_api.domain.answer.dtos;

import com.eduforum.api.forum_api.domain.answer.model.Answer;
import com.eduforum.api.forum_api.domain.topic.model.Topic;

public record GetAllAnswerByTopic(
    Long id,
    String message
) {
  public GetAllAnswerByTopic(Answer answer) {
    this(answer.getId(), answer.getMessage());
  }
}
