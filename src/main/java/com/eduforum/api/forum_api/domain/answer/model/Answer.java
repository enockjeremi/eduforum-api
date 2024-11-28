package com.eduforum.api.forum_api.domain.answer.model;

import com.eduforum.api.forum_api.domain.answer.dtos.UpdateAnswerDTO;
import com.eduforum.api.forum_api.domain.topic.model.Topic;
import com.eduforum.api.forum_api.domain.user.model.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.Instant;

@Entity
@Table(name = "answers")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "Id")
public class Answer {
  @jakarta.persistence.Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long Id;

  private String message;
  private Boolean solution;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "topic_id", nullable = false)
  private Topic topic;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "user_id", nullable = false)
  private User user;

  @CreationTimestamp
  private Instant createdOn;
  @UpdateTimestamp
  private Instant lastUpdatedOn;

  public Answer(String message, Topic topic, User user) {
    this.solution = false;
    this.message = message;
    this.topic = topic;
    this.user = user;
  }

  public void updateAnswer(UpdateAnswerDTO payload) {
    if (payload.message() != null) {
      this.message = payload.message();
    }
  }

  public void isSolution() {
    this.solution = true;
  }
}
