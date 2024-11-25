package com.eduforum.api.forum_api.controllers;

import com.eduforum.api.forum_api.domain.serializer.PageDTO;
import com.eduforum.api.forum_api.domain.serializer.PageMetadata;
import com.eduforum.api.forum_api.domain.serializer.Response;
import com.eduforum.api.forum_api.domain.serializer.Success;
import com.eduforum.api.forum_api.domain.topic.dtos.CreateTopicDTO;
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
                                              UriComponentsBuilder uriComponentsBuilder) {
    GetTopic topic = this.topicService.createTopic(payload);
    URI uri = uriComponentsBuilder.path("/topics/{id}").buildAndExpand(topic.id()).toUri();
    return ResponseEntity.created(uri).body(new Response(true, topic));
  }

  @GetMapping
  public ResponseEntity<PageDTO<GetTopic>> getAllTopic(@PageableDefault(
      size = 5
  ) Pageable pageable) {
    Page<GetTopic> page = this.topicService.getAllTopic(pageable);
    PageMetadata<GetTopic> pagination = new PageMetadata<GetTopic>(page);
    return ResponseEntity.ok(
        new PageDTO<GetTopic>(
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
  public ResponseEntity<Response> updateTopic(@PathVariable Long id, @RequestBody UpdateTopicDTO payload) {
    return ResponseEntity.ok().body(
        new Response(true, this.topicService.updateTopic(id, payload))
    );
  }

  @DeleteMapping("/{id}")
  @Transactional
  public ResponseEntity<Success> deleteTopic(@PathVariable Long id) {
    return ResponseEntity.ok().body(this.topicService.deleteTopic(id));
  }

  @PatchMapping("/{id}/status")
  @Transactional
  public ResponseEntity<Response> changeStatus(@PathVariable Long id) {
    GetTopic topic = this.topicService.changeStatus(id);
    return ResponseEntity.ok().body(
        new Response(true, topic)
    );
  }
}
