package com.eduforum.api.forum_api.controllers;

import com.eduforum.api.forum_api.domain.course.dtos.CreateCourseDTO;
import com.eduforum.api.forum_api.domain.course.dtos.GetCourse;
import com.eduforum.api.forum_api.domain.course.service.CourseService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/courses")
public class CourseController {

  private final CourseService courseService;

  @Autowired
  public CourseController(CourseService courseService) {
    this.courseService = courseService;
  }

  @PostMapping
  public GetCourse createCourse(@RequestBody @Valid CreateCourseDTO payload) {
    return this.courseService.createCourse(payload);
  }

  @GetMapping
  public List<GetCourse> getAllCourse(){
    return this.courseService.getAllCourse();
  }

}
