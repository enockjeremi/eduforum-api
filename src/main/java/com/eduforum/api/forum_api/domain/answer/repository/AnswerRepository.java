package com.eduforum.api.forum_api.domain.answer.repository;

import com.eduforum.api.forum_api.domain.answer.model.Answer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AnswerRepository extends JpaRepository<Answer, Long> {
  Page<Answer> findByTopicId(Pageable page, Long id);

}
