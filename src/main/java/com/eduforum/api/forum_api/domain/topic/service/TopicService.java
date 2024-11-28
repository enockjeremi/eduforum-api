package com.eduforum.api.forum_api.domain.topic.service;

import com.eduforum.api.forum_api.domain.course.model.Course;
import com.eduforum.api.forum_api.domain.course.service.CourseService;
import com.eduforum.api.forum_api.domain.serializer.Success;
import com.eduforum.api.forum_api.domain.topic.dtos.CreateTopicDTO;
import com.eduforum.api.forum_api.domain.topic.dtos.GetAllTopic;
import com.eduforum.api.forum_api.domain.topic.dtos.GetTopic;
import com.eduforum.api.forum_api.domain.topic.dtos.UpdateTopicDTO;
import com.eduforum.api.forum_api.domain.topic.model.Topic;
import com.eduforum.api.forum_api.domain.topic.repository.TopicRepository;
import com.eduforum.api.forum_api.domain.user.model.Role;
import com.eduforum.api.forum_api.domain.user.service.UserService;
import com.eduforum.api.forum_api.infra.errors.ForbiddenException;
import com.eduforum.api.forum_api.infra.errors.ValidateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TopicService {

  private final TopicRepository topicRepository;
  private final CourseService courseService;
  private final UserService userService;

  @Autowired
  public TopicService(TopicRepository topicRepository, CourseService courseService, UserService userService) {
    this.topicRepository = topicRepository;
    this.courseService = courseService;
    this.userService = userService;
  }

  public GetTopic createTopic(CreateTopicDTO payload, String email) {
    var user = this.userService.findUserByEmail(email);
    Course course = this.courseService.findCourse(payload.idCourse());
    Topic topic = this.topicRepository.save(new Topic(course, payload.content(), payload.title(), user));
    return new GetTopic(topic);
  }

  public Page<GetAllTopic> getAllTopic(Pageable pageable) {
    return this.topicRepository.findAll(pageable).map(GetAllTopic::new);
  }

  public Page<GetAllTopic> findByTitle(Pageable pageable, String title) {
    return this.topicRepository.findByTitleContainsIgnoreCase(pageable, title).map(GetAllTopic::new);
  }

  public Topic findTopic(Long id) {
    return this.topicRepository.findById(id).orElseThrow(() ->
        new ValidateException("topic with id (" + id + ") not found")
    );
  }

  public GetTopic getTopic(Long id) {
    Topic topic = this.findTopic(id);
    return new GetTopic(topic);
  }

  public GetTopic updateTopic(Long id, UpdateTopicDTO payload, String email) {
    Topic topic = this.findTopic(id);
    verifyAdminOrAuthor(topic, email);

    topic.updateTopic(payload);
    return new GetTopic(topic);
  }

  public Success deleteTopic(Long id, String email) {
    Topic topic = this.findTopic(id);
    verifyAdminOrAuthor(topic, email);

    this.topicRepository.delete(topic);
    return new Success(true, "topic with id (" + id + ") deleted");
  }

  public GetTopic changeStatus(Long id, String email) {
    Topic topic = this.findTopic(id);
    verifyAdminOrAuthor(topic, email);

    topic.changeStatus();
    return new GetTopic(topic);
  }

  public Page<GetAllTopic> findByCourseId(Pageable pageable, Long idCourse) {
    return this.topicRepository.findByCourseId(pageable, idCourse).map(GetAllTopic::new);
  }

  public void verifyAuthor(Topic topic, String email) {
    var user = this.userService.findUserByEmail(email);
    if (topic.getUser() != user) {
      throw new ForbiddenException("permission denied.");
    }
  }

  public void verifyAdminOrAuthor(Topic topic, String email) {
    var user = this.userService.findUserByEmail(email);
    boolean isAdmin = user.getRoles().stream()
        .anyMatch(role -> "ROLE_ADMIN".equals(role.getName()));
    if (!isAdmin && !topic.getUser().equals(user)) {
      throw new ForbiddenException("Permission denied.");
    }
  }


  public Page<GetAllTopic> findTopicsByCourseCategory(Pageable pageable, String category) {
    return this.topicRepository.findTopicsByCourseCategory(pageable, category).map(GetAllTopic::new);
  }
}
