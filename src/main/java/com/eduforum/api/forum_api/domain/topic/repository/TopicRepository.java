package com.eduforum.api.forum_api.domain.topic.repository;

import com.eduforum.api.forum_api.domain.topic.model.Topic;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface TopicRepository extends JpaRepository<Topic, Long> {
  Page<Topic> findByTitleContainsIgnoreCase(Pageable pageable, String title);
  Page<Topic> findByCourseId(Pageable pageable, Long idCourse);
  @Query("SELECT t FROM Topic t JOIN t.course c WHERE c.category = :category")
  Page<Topic> findTopicsByCourseCategory(Pageable pageable, @Param("category") String category);
}
