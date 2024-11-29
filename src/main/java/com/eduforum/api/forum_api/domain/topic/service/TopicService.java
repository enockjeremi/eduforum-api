package com.eduforum.api.forum_api.domain.topic.service;

import com.eduforum.api.forum_api.domain.answer.model.Answer;
import com.eduforum.api.forum_api.domain.answer.repository.AnswerRepository;
import com.eduforum.api.forum_api.domain.course.model.Course;
import com.eduforum.api.forum_api.domain.course.service.CourseService;
import com.eduforum.api.forum_api.domain.serializer.Success;
import com.eduforum.api.forum_api.domain.topic.dtos.CreateTopicDTO;
import com.eduforum.api.forum_api.domain.topic.dtos.GetTopicWithAuthor;
import com.eduforum.api.forum_api.domain.topic.dtos.GetTopicWithOutAuthor;
import com.eduforum.api.forum_api.domain.topic.dtos.UpdateTopicDTO;
import com.eduforum.api.forum_api.domain.topic.model.Topic;
import com.eduforum.api.forum_api.domain.topic.repository.TopicRepository;
import com.eduforum.api.forum_api.domain.user.service.UserService;
import com.eduforum.api.forum_api.infra.errors.ForbiddenException;
import com.eduforum.api.forum_api.infra.errors.ValidateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class TopicService {

  private final TopicRepository topicRepository;
  private final AnswerRepository answerRepository;
  private final CourseService courseService;
  private final UserService userService;

  @Autowired
  public TopicService(TopicRepository topicRepository, CourseService courseService,
                      UserService userService, AnswerRepository answerRepository
  ) {
    this.answerRepository = answerRepository;
    this.topicRepository = topicRepository;
    this.courseService = courseService;
    this.userService = userService;
  }

  public GetTopicWithAuthor createTopic(CreateTopicDTO payload, String email) {
    var user = this.userService.findUserByEmail(email);
    Course course = this.courseService.findCourse(payload.idCourse());
    Topic topic = this.topicRepository.save(new Topic(course, payload.content(), payload.title(), user));
    return new GetTopicWithAuthor(topic);
  }

  public Page<GetTopicWithOutAuthor> getAllTopic(Pageable pageable) {
    return this.topicRepository.findAll(pageable).map(GetTopicWithOutAuthor::new);
  }

  public Page<GetTopicWithOutAuthor> findByTitle(Pageable pageable, String title) {
    return this.topicRepository.findByTitleContainsIgnoreCase(pageable, title).map(GetTopicWithOutAuthor::new);
  }

  public Topic findTopic(Long id) {
    return this.topicRepository.findById(id).orElseThrow(() ->
        new ValidateException("topic with id (" + id + ") not found")
    );
  }

  public GetTopicWithAuthor getTopic(Long id) {
    Topic topic = this.findTopic(id);
    return new GetTopicWithAuthor(topic);
  }

  public GetTopicWithAuthor updateTopic(Long id, UpdateTopicDTO payload, String email) {
    Topic topic = this.findTopic(id);
    verifyAdminOrAuthor(topic, email);

    topic.updateTopic(payload);
    return new GetTopicWithAuthor(topic);
  }

  public Success deleteTopic(Long id, String email) {
    Topic topic = this.findTopic(id);
    verifyAdminOrAuthor(topic, email);

    this.topicRepository.delete(topic);
    return new Success(true, "topic with id (" + id + ") deleted");
  }

  public Page<GetTopicWithOutAuthor> findByCourseId(Pageable pageable, Long idCourse) {
    return this.topicRepository.findByCourseId(pageable, idCourse).map(GetTopicWithOutAuthor::new);
  }


  public void verifyAdminOrAuthor(Topic topic, String email) {
    var user = this.userService.findUserByEmail(email);
    boolean isAdmin = user.getRoles().stream()
        .anyMatch(role -> "ROLE_ADMIN".equals(role.getName()));
    if (!isAdmin && !topic.getUser().equals(user)) {
      throw new ForbiddenException("permission denied.");
    }
  }


  public Page<GetTopicWithOutAuthor> findTopicsByCourseCategory(Pageable pageable, String category) {
    return this.topicRepository.findTopicsByCourseCategory(pageable, category).map(GetTopicWithOutAuthor::new);
  }

  public GetTopicWithAuthor solutionAnswer(Long idTopic, Long idAnswer, String email) {
    Topic topic = this.findTopic(idTopic);
    verifyAdminOrAuthor(topic, email);
    Answer answer = this.answerRepository.findById(idAnswer).orElseThrow(() ->
        new ValidateException("answer with id (" + idAnswer + ") not found"));
    var isAnswerValid = topic.getAnswers().stream().anyMatch(a -> a.equals(answer));
    if (!isAnswerValid) {
      throw new ValidateException("answer with id (" + idAnswer + ") not found");
    }
    topic.addSolution(answer);
    answer.isSolution();
    return new GetTopicWithAuthor(topic);
  }
}
