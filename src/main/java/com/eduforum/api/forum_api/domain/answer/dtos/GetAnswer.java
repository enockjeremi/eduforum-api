package com.eduforum.api.forum_api.domain.answer.dtos;

import com.eduforum.api.forum_api.domain.answer.model.Answer;
import com.eduforum.api.forum_api.domain.topic.dtos.GetTopic;
import com.eduforum.api.forum_api.domain.topic.model.Topic;

public record GetAnswer(
    Long id,
    String message,
    GetTopic topic
) {
  public GetAnswer(Answer answer) {
    this(answer.getId(), answer.getMessage(), new GetTopic(answer.getTopic()));
  }
}
