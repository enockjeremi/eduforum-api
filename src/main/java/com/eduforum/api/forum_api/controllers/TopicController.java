package com.eduforum.api.forum_api.controllers;

import com.eduforum.api.forum_api.domain.serializer.PageDTO;
import com.eduforum.api.forum_api.domain.serializer.PageMetadata;
import com.eduforum.api.forum_api.domain.serializer.Response;
import com.eduforum.api.forum_api.domain.serializer.Success;
import com.eduforum.api.forum_api.domain.topic.dtos.CreateTopicDTO;
import com.eduforum.api.forum_api.domain.topic.dtos.GetAllTopic;
import com.eduforum.api.forum_api.domain.topic.dtos.GetTopic;
import com.eduforum.api.forum_api.domain.topic.dtos.UpdateTopicDTO;
import com.eduforum.api.forum_api.domain.topic.service.TopicService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/topics")
public class TopicController {

  private final TopicService topicService;

  @Autowired
  public TopicController(TopicService topicService) {
    this.topicService = topicService;
  }

  @PostMapping
  public ResponseEntity<Response> createTopic(@RequestBody @Valid CreateTopicDTO payload,
                                              UriComponentsBuilder uriComponentsBuilder,
                                              Authentication authentication) {
    UserDetails user = (UserDetails) authentication.getPrincipal();

    GetTopic topic = this.topicService.createTopic(payload, user.getUsername());
    URI uri = uriComponentsBuilder.path("/topics/{id}").buildAndExpand(topic.id()).toUri();
    return ResponseEntity.created(uri).body(new Response(true, topic));
  }

  @GetMapping
  public ResponseEntity<PageDTO<GetAllTopic>> getAllTopic(
      @PageableDefault(size = 5) Pageable pageable,
      @RequestParam(required = false) String search) {
    Page<GetAllTopic> page;
    if (search != null) {
      page = this.topicService.findByTitle(pageable, search);
    } else {
      page = this.topicService.getAllTopic(pageable);
    }
    PageMetadata<GetAllTopic> pagination = new PageMetadata<GetAllTopic>(page);
    return ResponseEntity.ok(
        new PageDTO<GetAllTopic>(
            page.getContent(),
            pagination
        ));
  }

  @GetMapping("/{id}")
  public ResponseEntity<Response> getTopic(@PathVariable Long id) {
    return ResponseEntity.ok().body(
        new Response(true, this.topicService.getTopic(id))
    );
  }

  @PutMapping("/{id}")
  @Transactional
  public ResponseEntity<Response> updateTopic(@PathVariable Long id,
                                              @RequestBody UpdateTopicDTO payload,
                                              Authentication authentication) {
    UserDetails user = (UserDetails) authentication.getPrincipal();

    return ResponseEntity.ok().body(
        new Response(true, this.topicService.updateTopic(id, payload, user.getUsername()))
    );
  }

  @DeleteMapping("/{id}")
  @Transactional
  public ResponseEntity<Success> deleteTopic(@PathVariable Long id, Authentication authentication) {
    UserDetails user = (UserDetails) authentication.getPrincipal();

    return ResponseEntity.ok().body(this.topicService.deleteTopic(id, user.getUsername()));
  }

  @PatchMapping("/{id}/status")
  @Transactional
  public ResponseEntity<Response> changeStatus(@PathVariable Long id, Authentication authentication) {
    UserDetails user = (UserDetails) authentication.getPrincipal();

    GetTopic topic = this.topicService.changeStatus(id, user.getUsername());
    return ResponseEntity.ok().body(
        new Response(true, topic)
    );
  }

  @GetMapping("/c/{idCourse}")
  public ResponseEntity<PageDTO<GetAllTopic>> getTopicByCourse(
      @PageableDefault(size = 5) Pageable pageable,
      @PathVariable Long idCourse) {
    Page<GetAllTopic> page = this.topicService.findByCourseId(pageable, idCourse);

    PageMetadata<GetAllTopic> pagination = new PageMetadata<GetAllTopic>(page);
    return ResponseEntity.ok(
        new PageDTO<GetAllTopic>(
            page.getContent(),
            pagination
        ));
  }

  @GetMapping("/category/{category}")
  public ResponseEntity<PageDTO<GetAllTopic>> getTopicByCategoryCourse(
      @PageableDefault(size = 5) Pageable pageable,
      @PathVariable String category) {

    Page<GetAllTopic> page = this.topicService.findTopicsByCourseCategory(pageable, category.replace("+", " ").trim());

    PageMetadata<GetAllTopic> pagination = new PageMetadata<GetAllTopic>(page);
    return ResponseEntity.ok(
        new PageDTO<GetAllTopic>(
            page.getContent(),
            pagination
        ));
  }
}
