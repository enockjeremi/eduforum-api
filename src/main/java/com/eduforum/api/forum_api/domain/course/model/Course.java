package com.eduforum.api.forum_api.domain.course.model;

import com.eduforum.api.forum_api.domain.course.dtos.CreateCourseDTO;
import com.eduforum.api.forum_api.domain.topic.model.Topic;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "courses")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "Id")
public class Course {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long Id;
  private String name;
  private String category;

  @OneToMany(mappedBy = "course", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
  private List<Topic> topic;

  public Course(CreateCourseDTO courseDTO){
    this.name = courseDTO.name();
    this.category = courseDTO.category();
  }

}
