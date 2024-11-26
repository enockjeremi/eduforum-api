package com.eduforum.api.forum_api.controllers;


import com.eduforum.api.forum_api.domain.answer.dtos.CreateAnswerDTO;
import com.eduforum.api.forum_api.domain.answer.dtos.GetAllAnswerByTopic;
import com.eduforum.api.forum_api.domain.answer.dtos.GetAnswer;
import com.eduforum.api.forum_api.domain.answer.dtos.UpdateAnswerDTO;
import com.eduforum.api.forum_api.domain.answer.service.AnswerService;
import com.eduforum.api.forum_api.domain.serializer.PageDTO;
import com.eduforum.api.forum_api.domain.serializer.PageMetadata;
import com.eduforum.api.forum_api.domain.serializer.Response;
import com.eduforum.api.forum_api.domain.serializer.Success;
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
@RequestMapping("/answers")
public class AnswerController {

  private final AnswerService answerService;

  @Autowired
  public AnswerController(AnswerService answerService) {
    this.answerService = answerService;
  }

  @PostMapping
  public ResponseEntity<Response> createAnswer(@RequestBody @Valid CreateAnswerDTO payload,
                                               UriComponentsBuilder uriComponentsBuilder) {
    GetAnswer answer = this.answerService.createAnswer(payload);
    URI uri = uriComponentsBuilder.path("/answers/{id}").buildAndExpand(answer.id()).toUri();
    return ResponseEntity.created(uri).body(
        new Response(true, answer)
    );
  }

  @GetMapping("/t/{idTopic}")
  ResponseEntity<PageDTO<GetAllAnswerByTopic>> getAllAnswerByTopic(@PageableDefault(
      size = 5
  ) Pageable pageable, @PathVariable Long idTopic) {
    Page<GetAllAnswerByTopic> page = this.answerService.getAllAnswerByTopic(pageable, idTopic);
    PageMetadata<GetAllAnswerByTopic> pagination = new PageMetadata<GetAllAnswerByTopic>(page);
    return ResponseEntity.ok(
        new PageDTO<GetAllAnswerByTopic>(
            page.getContent(),
            pagination
        ));
  }

  @PutMapping("/{id}")
  @Transactional
  public ResponseEntity<Response> updateAnswer(@PathVariable Long id, @RequestBody UpdateAnswerDTO payload) {
    return ResponseEntity.ok().body(
        new Response(true, this.answerService.updateAnswer(id, payload))
    );
  }

  @DeleteMapping("/{id}")
  @Transactional
  public ResponseEntity<Success> deleteAnswer(@PathVariable Long id) {
    return ResponseEntity.ok().body(this.answerService.deleteAnswer(id));
  }
}
