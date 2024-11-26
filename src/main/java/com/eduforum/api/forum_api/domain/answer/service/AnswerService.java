package com.eduforum.api.forum_api.domain.answer.service;

import com.eduforum.api.forum_api.domain.answer.dtos.CreateAnswerDTO;
import com.eduforum.api.forum_api.domain.answer.dtos.GetAllAnswerByTopic;
import com.eduforum.api.forum_api.domain.answer.dtos.GetAnswer;
import com.eduforum.api.forum_api.domain.answer.dtos.UpdateAnswerDTO;
import com.eduforum.api.forum_api.domain.answer.model.Answer;
import com.eduforum.api.forum_api.domain.answer.repository.AnswerRepository;
import com.eduforum.api.forum_api.domain.serializer.Success;
import com.eduforum.api.forum_api.domain.topic.service.TopicService;
import com.eduforum.api.forum_api.infra.errors.ValidateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class AnswerService {

  private final AnswerRepository answerRepository;
  private final TopicService topicService;

  @Autowired
  public AnswerService(AnswerRepository answerRepository, TopicService topicService) {
    this.answerRepository = answerRepository;
    this.topicService = topicService;
  }

  public GetAnswer createAnswer(CreateAnswerDTO payload){
    var topic = this.topicService.findTopic(payload.idTopic());
    Answer answer = this.answerRepository.save(new Answer(payload.message(), topic));
    return new GetAnswer(answer);
  }

  public Page<GetAllAnswerByTopic> getAllAnswerByTopic(Pageable pageable, Long idTopic) {
    var topic = this.topicService.findTopic(idTopic);
    return this.answerRepository.findByTopicId(pageable, topic.getId()).map(GetAllAnswerByTopic::new);
  }

  public Answer findAnswer(Long id) {
    return this.answerRepository.findById(id).orElseThrow(() ->
        new ValidateException("answer with id (" + id + ") not found")
    );
  }

  public GetAnswer updateAnswer(Long id, UpdateAnswerDTO payload) {
    Answer answer = this.findAnswer(id);
    answer.updateAnswer(payload);
    return new GetAnswer(answer);
  }

  public Success deleteAnswer(Long id) {
    Answer answer = this.findAnswer(id);
    this.answerRepository.delete(answer);
    return new Success(true, "answer with id (" + id + ") deleted");
  }
}
