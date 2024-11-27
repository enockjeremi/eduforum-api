package com.eduforum.api.forum_api.domain.answer.dtos;

import com.eduforum.api.forum_api.domain.answer.model.Answer;
import com.eduforum.api.forum_api.domain.user.dtos.GetUserWithOutProfile;

public record GetAllAnswerByTopic(
    Long id,
    String message,
    GetUserWithOutProfile author
) {
  public GetAllAnswerByTopic(Answer answer) {
    this(answer.getId(), answer.getMessage(), new GetUserWithOutProfile(answer.getUser()));
  }
}
