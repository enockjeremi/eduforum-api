package com.eduforum.api.forum_api.domain.answer.service;

import com.eduforum.api.forum_api.domain.answer.dtos.CreateAnswerDTO;
import com.eduforum.api.forum_api.domain.answer.dtos.GetAnswer;
import com.eduforum.api.forum_api.domain.answer.dtos.UpdateAnswerDTO;
import com.eduforum.api.forum_api.domain.answer.model.Answer;
import com.eduforum.api.forum_api.domain.answer.repository.AnswerRepository;
import com.eduforum.api.forum_api.domain.serializer.Success;
import com.eduforum.api.forum_api.domain.topic.service.TopicService;
import com.eduforum.api.forum_api.domain.user.service.UserService;
import com.eduforum.api.forum_api.infra.errors.ForbiddenException;
import com.eduforum.api.forum_api.infra.errors.ValidateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class AnswerService {

  private final AnswerRepository answerRepository;
  private final TopicService topicService;
  private final UserService userService;

  @Autowired
  public AnswerService(AnswerRepository answerRepository, TopicService topicService, UserService userService) {
    this.answerRepository = answerRepository;
    this.topicService = topicService;
    this.userService = userService;
  }

  public GetAnswer createAnswer(CreateAnswerDTO payload, String email){
    var user = this.userService.findUserByEmail(email);

    var topic = this.topicService.findTopic(payload.idTopic());
    Answer answer = this.answerRepository.save(new Answer(payload.message(), topic, user));
    return new GetAnswer(answer);
  }

  public Page<GetAnswer> getAllAnswerByTopic(Pageable pageable, Long idTopic) {
    var topic = this.topicService.findTopic(idTopic);
    return this.answerRepository.findByTopicId(pageable, topic.getId()).map(GetAnswer::new);
  }

  public Answer findAnswer(Long id) {
    return this.answerRepository.findById(id).orElseThrow(() ->
        new ValidateException("answer with id (" + id + ") not found")
    );
  }

  public GetAnswer updateAnswer(Long id, UpdateAnswerDTO payload, String email) {
    Answer answer = this.findAnswer(id);
    verifyAuthor(answer, email);

    answer.updateAnswer(payload);
    return new GetAnswer(answer);
  }

  public Success deleteAnswer(Long id, String email) {
    Answer answer = this.findAnswer(id);
    verifyAuthor(answer, email);

    this.answerRepository.delete(answer);
    return new Success(true, "answer with id (" + id + ") deleted");
  }

  public void verifyAuthor(Answer answer, String email) {
    var user = this.userService.findUserByEmail(email);
    if (answer.getUser() != user) {
      throw new ForbiddenException("permission denied.");
    }
  }
}
