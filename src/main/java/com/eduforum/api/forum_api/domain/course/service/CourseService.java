package com.eduforum.api.forum_api.domain.course.service;

import com.eduforum.api.forum_api.domain.answer.model.Answer;
import com.eduforum.api.forum_api.domain.course.dtos.CreateCourseDTO;
import com.eduforum.api.forum_api.domain.course.dtos.GetCourse;
import com.eduforum.api.forum_api.domain.course.dtos.UpdateCourseDTO;
import com.eduforum.api.forum_api.domain.course.model.Course;
import com.eduforum.api.forum_api.domain.course.repository.CourseRepository;
import com.eduforum.api.forum_api.domain.serializer.Success;
import com.eduforum.api.forum_api.infra.errors.ValidateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

  public Page<GetCourse> getAllCourses(Pageable pageable) {
    return this.courseRepository.findAll(pageable).map(GetCourse::new);
  }

  public Course findCourse(Long id) {
    return this.courseRepository.findById(id).orElseThrow(() ->
        new ValidateException("course with id (" + id + ") not found")
    );
  }

  public GetCourse getCourse(Long id) {
    Course course = this.findCourse(id);
    return new GetCourse(course);
  }

  public GetCourse updateCourse(Long id, UpdateCourseDTO payload) {
    Course course = this.findCourse(id);
    course.updateCourse(payload);
    return new GetCourse(course);
  }

  public Success deleteCourse(Long id) {
    Course course = this.findCourse(id);
    this.courseRepository.delete(course);
    return new Success(true, "course with id (" + id + ") deleted");
  }
}
