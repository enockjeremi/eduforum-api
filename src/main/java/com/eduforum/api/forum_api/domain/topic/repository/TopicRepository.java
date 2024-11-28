package com.eduforum.api.forum_api.domain.topic.repository;

import com.eduforum.api.forum_api.domain.topic.model.Topic;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Pageable;

public interface TopicRepository extends JpaRepository<Topic, Long> {
  Page<Topic> findByTitleContainsIgnoreCase(Pageable pageable, String title);
}
