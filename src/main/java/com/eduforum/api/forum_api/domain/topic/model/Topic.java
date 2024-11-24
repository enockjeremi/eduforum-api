package com.eduforum.api.forum_api.domain.topic.model;

import com.eduforum.api.forum_api.domain.answer.model.Answer;
import com.eduforum.api.forum_api.domain.course.model.Course;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.Instant;
import java.util.List;

@Entity
@Table(name = "topics")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "Id")
public class Topic {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long Id;

  private String title;
  private String content;
  private Boolean status;

  @ManyToOne
  private Course course;

  @OneToMany(mappedBy = "topic", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
  private List<Answer> answers;

  @CreationTimestamp
  private Instant createdOn;
  @UpdateTimestamp
  private Instant lastUpdatedOn;
}
