package com.eduforum.api.forum_api.controllers;

import com.eduforum.api.forum_api.domain.course.dtos.CreateCourseDTO;
import com.eduforum.api.forum_api.domain.course.dtos.GetCourse;
import com.eduforum.api.forum_api.domain.course.dtos.UpdateCourseDTO;
import com.eduforum.api.forum_api.domain.course.service.CourseService;
import com.eduforum.api.forum_api.domain.serializer.PageDTO;
import com.eduforum.api.forum_api.domain.serializer.PageMetadata;
import com.eduforum.api.forum_api.domain.serializer.Response;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
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

@Tag(name = "Course")
@RestController
@RequestMapping("/courses")
@SecurityRequirement(name = "bearer-key")
public class CourseController {

  private final CourseService courseService;

  @Autowired
  public CourseController(CourseService courseService) {
    this.courseService = courseService;
  }

  @Operation(summary = "Create new course - Only Admin")
  @PostMapping
  public ResponseEntity<Response> createCourse(@RequestBody @Valid CreateCourseDTO payload,
                                               UriComponentsBuilder uriComponentsBuilder) {
    GetCourse course = this.courseService.createCourse(payload);
    URI uri = uriComponentsBuilder.path("/courses/{id}").buildAndExpand(course.id()).toUri();
    return ResponseEntity.created(uri).body(
        new Response(true, course)
    );
  }

  @Operation(summary = "Get all courses")
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

  @Operation(summary = "Get course by id")
  @GetMapping("/{id}")
  public ResponseEntity<Response> getCourse(@PathVariable Long id) {
    return ResponseEntity.ok().body(
        new Response(true, this.courseService.getCourse(id))
    );
  }

  @Operation(summary = "Update course - Only Admin")
  @PutMapping("/{id}")
  @Transactional
  public ResponseEntity<Response> updateCourse(@PathVariable Long id, @RequestBody UpdateCourseDTO payload) {
    return ResponseEntity.ok().body(
        new Response(true, this.courseService.updateCourse(id, payload))
    );
  }

  @Operation(summary = "Delete course - Only Admin")
  @DeleteMapping("/{id}")
  @Transactional
  public ResponseEntity<Response> deleteCourse(@PathVariable Long id) {
    return ResponseEntity.ok().body(
        new Response(true, this.courseService.deleteCourse(id))
    );
  }
}
