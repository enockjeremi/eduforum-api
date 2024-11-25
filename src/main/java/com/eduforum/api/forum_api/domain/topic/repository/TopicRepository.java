package com.eduforum.api.forum_api.domain.topic.repository;

import com.eduforum.api.forum_api.domain.topic.model.Topic;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TopicRepository extends JpaRepository<Topic, Long> {
}
