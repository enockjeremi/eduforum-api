package com.eduforum.api.forum_api.domain.answer.model;

import com.eduforum.api.forum_api.domain.topic.model.Topic;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.Instant;

@Entity
@Table(name = "topics")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "Id")
public class Answer {
  @jakarta.persistence.Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long Id;

  String message;
  private Boolean solution;

  @ManyToOne
  private Topic topic;

  @CreationTimestamp
  private Instant createdOn;
  @UpdateTimestamp
  private Instant lastUpdatedOn;

}
