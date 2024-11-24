package com.eduforum.api.forum_api.domain.course.service;

import com.eduforum.api.forum_api.domain.course.dtos.CreateCourseDTO;
import com.eduforum.api.forum_api.domain.course.dtos.GetCourse;
import com.eduforum.api.forum_api.domain.course.model.Course;
import com.eduforum.api.forum_api.domain.course.repository.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CourseService {

  private final CourseRepository courseRepository;

  @Autowired
  public CourseService(CourseRepository courseRepository) {
    this.courseRepository = courseRepository;
  }

  public GetCourse createCourse(CreateCourseDTO courseDTO) {
    var course = this.courseRepository.save(new Course(courseDTO));
    return new GetCourse(course);
  }

  public List<GetCourse> getAllCourse() {
    return this.courseRepository.findAll().stream().<GetCourse>map(GetCourse::new).toList();
  }

}
