package com.eduforum.api.forum_api.domain.topic.model;

import com.eduforum.api.forum_api.domain.answer.model.Answer;
import com.eduforum.api.forum_api.domain.course.model.Course;
import com.eduforum.api.forum_api.domain.topic.dtos.UpdateTopicDTO;
import com.eduforum.api.forum_api.domain.user.model.User;
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

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "course_id", nullable = false)
  private Course course;

  @OneToMany(mappedBy = "topic", cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
  private List<Answer> answers;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "user_id", nullable = false)
  private User user;

  @OneToOne
  @JoinColumn(name = "solution_answer_id", referencedColumnName = "id")
  private Answer solutionAnswer;

  @CreationTimestamp
  private Instant createdOn;
  @UpdateTimestamp
  private Instant lastUpdatedOn;

  public Topic(Course course, String content, String title, User user) {
    this.status = true;
    this.course = course;
    this.content = content;
    this.title = title;
    this.user = user;
  }

  public void updateTopic(UpdateTopicDTO payload) {
    if (payload.title() != null) {
      this.title = payload.title();
    } else if (payload.content() != null) {
      this.content = payload.content();
    }
  }

  public void changeStatus() {
    this.status = !this.status;
  }

  public void addSolution(Answer answer) {
    this.solutionAnswer = answer;
    this.status = false;
  }
}
