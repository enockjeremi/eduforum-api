package com.eduforum.api.forum_api.domain.course.repository;

import com.eduforum.api.forum_api.domain.course.model.Course;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CourseRepository extends JpaRepository<Course, Long> {
}
