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
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
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
                                               UriComponentsBuilder uriComponentsBuilder,
                                               Authentication authentication) {
    UserDetails user = (UserDetails) authentication.getPrincipal();

    GetAnswer answer = this.answerService.createAnswer(payload, user.getUsername());
    URI uri = uriComponentsBuilder.path("/answers/t/{id}").buildAndExpand(payload.idTopic()).toUri();
    return ResponseEntity.created(uri).body(
        new Response(true, answer)
    );
  }

  @GetMapping("/t/{idTopic}")
  ResponseEntity<PageDTO<GetAnswer>> getAllAnswerByTopic(@PageableDefault(
      size = 5
  ) Pageable pageable, @PathVariable Long idTopic) {
    Page<GetAnswer> page = this.answerService.getAllAnswerByTopic(pageable, idTopic);
    PageMetadata<GetAnswer> pagination = new PageMetadata<GetAnswer>(page);
    return ResponseEntity.ok(
        new PageDTO<GetAnswer>(
            page.getContent(),
            pagination
        ));
  }

  @PutMapping("/{id}")
  @Transactional
  public ResponseEntity<Response> updateAnswer(@PathVariable Long id,
                                               @RequestBody UpdateAnswerDTO payload,
                                               Authentication authentication) {
    UserDetails user = (UserDetails) authentication.getPrincipal();

    return ResponseEntity.ok().body(
        new Response(true, this.answerService.updateAnswer(id, payload, user.getUsername()))
    );
  }

  @DeleteMapping("/{id}")
  @Transactional
  public ResponseEntity<Success> deleteAnswer(@PathVariable Long id, Authentication authentication) {
    UserDetails user = (UserDetails) authentication.getPrincipal();

    return ResponseEntity.ok().body(this.answerService.deleteAnswer(id, user.getUsername()));
  }
}
