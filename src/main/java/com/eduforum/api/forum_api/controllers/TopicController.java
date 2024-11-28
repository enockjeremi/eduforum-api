package com.eduforum.api.forum_api.controllers;

import com.eduforum.api.forum_api.domain.serializer.PageDTO;
import com.eduforum.api.forum_api.domain.serializer.PageMetadata;
import com.eduforum.api.forum_api.domain.serializer.Response;
import com.eduforum.api.forum_api.domain.serializer.Success;
import com.eduforum.api.forum_api.domain.topic.dtos.CreateTopicDTO;
import com.eduforum.api.forum_api.domain.topic.dtos.GetTopicWithOutAuthor;
import com.eduforum.api.forum_api.domain.topic.dtos.GetTopicWithAuthor;
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

    GetTopicWithAuthor topic = this.topicService.createTopic(payload, user.getUsername());
    URI uri = uriComponentsBuilder.path("/topics/{id}").buildAndExpand(topic.id()).toUri();
    return ResponseEntity.created(uri).body(new Response(true, topic));
  }

  @GetMapping
  public ResponseEntity<PageDTO<GetTopicWithOutAuthor>> getAllTopic(
      @PageableDefault(size = 5) Pageable pageable,
      @RequestParam(required = false) String search) {
    Page<GetTopicWithOutAuthor> page;
    if (search != null) {
      page = this.topicService.findByTitle(pageable, search);
    } else {
      page = this.topicService.getAllTopic(pageable);
    }
    PageMetadata<GetTopicWithOutAuthor> pagination = new PageMetadata<GetTopicWithOutAuthor>(page);
    return ResponseEntity.ok(
        new PageDTO<GetTopicWithOutAuthor>(
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

  @GetMapping("/c/{idCourse}")
  public ResponseEntity<PageDTO<GetTopicWithOutAuthor>> getTopicByCourse(
      @PageableDefault(size = 5) Pageable pageable,
      @PathVariable Long idCourse) {
    Page<GetTopicWithOutAuthor> page = this.topicService.findByCourseId(pageable, idCourse);

    PageMetadata<GetTopicWithOutAuthor> pagination = new PageMetadata<GetTopicWithOutAuthor>(page);
    return ResponseEntity.ok(
        new PageDTO<GetTopicWithOutAuthor>(
            page.getContent(),
            pagination
        ));
  }

  @GetMapping("/category/{category}")
  public ResponseEntity<PageDTO<GetTopicWithOutAuthor>> getTopicByCategoryCourse(
      @PageableDefault(size = 5) Pageable pageable,
      @PathVariable String category) {

    Page<GetTopicWithOutAuthor> page = this.topicService.findTopicsByCourseCategory(pageable, category.replace("+", " ").trim());

    PageMetadata<GetTopicWithOutAuthor> pagination = new PageMetadata<GetTopicWithOutAuthor>(page);
    return ResponseEntity.ok(
        new PageDTO<GetTopicWithOutAuthor>(
            page.getContent(),
            pagination
        ));
  }

  @PatchMapping("/{idTopic}/s/{idAnswer}")
  @Transactional
  public ResponseEntity<Response> solutionAnswer(
      @PathVariable Long idTopic,
      @PathVariable Long idAnswer,
      Authentication authentication) {
    UserDetails user = (UserDetails) authentication.getPrincipal();

    GetTopicWithAuthor topic = this.topicService.solutionAnswer(idTopic,idAnswer, user.getUsername());
    return ResponseEntity.ok().body(
        new Response(true, topic)
    );
  }

}
