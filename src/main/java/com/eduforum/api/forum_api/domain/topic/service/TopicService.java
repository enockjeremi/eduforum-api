package com.eduforum.api.forum_api.domain.topic.service;

import com.eduforum.api.forum_api.domain.course.model.Course;
import com.eduforum.api.forum_api.domain.course.service.CourseService;
import com.eduforum.api.forum_api.domain.serializer.Success;
import com.eduforum.api.forum_api.domain.topic.dtos.CreateTopicDTO;
import com.eduforum.api.forum_api.domain.topic.dtos.GetTopic;
import com.eduforum.api.forum_api.domain.topic.dtos.UpdateTopicDTO;
import com.eduforum.api.forum_api.domain.topic.model.Topic;
import com.eduforum.api.forum_api.domain.topic.repository.TopicRepository;
import com.eduforum.api.forum_api.infra.errors.ValidateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class TopicService {

  private final TopicRepository topicRepository;
  private final CourseService courseService;

  @Autowired
  public TopicService(TopicRepository topicRepository, CourseService courseService) {
    this.topicRepository = topicRepository;
    this.courseService = courseService;
  }

  public GetTopic createTopic(CreateTopicDTO payload) {
    Course course = this.courseService.findCourse(payload.idCourse());
    Topic topic = this.topicRepository.save(new Topic(course, payload.content(), payload.title()));
    return new GetTopic(topic);
  }

  public Page<GetTopic> getAllTopic(Pageable pageable) {
    return this.topicRepository.findAll(pageable).map(GetTopic::new);
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

  public GetTopic updateTopic(Long id, UpdateTopicDTO payload) {
    Topic topic = this.findTopic(id);
    topic.updateTopic(payload);
    return new GetTopic(topic);
  }

  public Success deleteTopic(Long id) {
    Topic topic = this.findTopic(id);
    this.topicRepository.delete(topic);
    return new Success(true, "topic with id (" + id + ") deleted");
  }

  public GetTopic changeStatus(Long id){
    Topic topic = this.findTopic(id);
    topic.changeStatus();
    return new GetTopic(topic);
  }
}
