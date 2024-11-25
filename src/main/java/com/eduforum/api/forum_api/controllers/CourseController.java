package com.eduforum.api.forum_api.controllers;

import com.eduforum.api.forum_api.domain.course.dtos.CreateCourseDTO;
import com.eduforum.api.forum_api.domain.course.dtos.GetCourse;
import com.eduforum.api.forum_api.domain.course.dtos.UpdateCourseDTO;
import com.eduforum.api.forum_api.domain.course.service.CourseService;
import com.eduforum.api.forum_api.domain.serializer.PageDTO;
import com.eduforum.api.forum_api.domain.serializer.PageMetadata;
import com.eduforum.api.forum_api.domain.serializer.Response;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/courses")
public class CourseController {

  private final CourseService courseService;

  @Autowired
  public CourseController(CourseService courseService) {
    this.courseService = courseService;
  }

  @PostMapping
  public ResponseEntity<Response> createCourse(@RequestBody @Valid CreateCourseDTO payload,
                                               UriComponentsBuilder uriComponentsBuilder) {
    GetCourse course = this.courseService.createCourse(payload);
    URI uri = uriComponentsBuilder.path("/courses/{id}").buildAndExpand(course.id()).toUri();
    return ResponseEntity.created(uri).body(
        new Response(true, course)
    );
  }

  @GetMapping
  public ResponseEntity<PageDTO<GetCourse>> getAllCourses(@PageableDefault(
      size = 5
  ) Pageable pageable) {
    Page<GetCourse> page = this.courseService.getAllCourses(pageable);
    PageMetadata<GetCourse> pagination = new PageMetadata<GetCourse>(page);
    return ResponseEntity.ok(
        new PageDTO<GetCourse>(
            page.getContent(),
            pagination
        ));
  }

  @GetMapping("/{id}")
  public ResponseEntity<Response> getCourse(@PathVariable Long id) {
    return ResponseEntity.ok().body(
        new Response(true, this.courseService.getCourse(id))
    );
  }

  @PutMapping("/{id}")
  @Transactional
  public ResponseEntity<Response> updateCourse(@PathVariable Long id, @RequestBody UpdateCourseDTO payload) {
    return ResponseEntity.ok().body(
        new Response(true, this.courseService.updateCourse(id, payload))
    );
  }
}
