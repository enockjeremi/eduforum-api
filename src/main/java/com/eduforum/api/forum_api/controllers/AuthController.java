package com.eduforum.api.forum_api.controllers;

import com.eduforum.api.forum_api.domain.serializer.Response;
import com.eduforum.api.forum_api.domain.user.dtos.CreateUserDTO;
import com.eduforum.api.forum_api.domain.user.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/auth")
public class AuthController {

  private final UserService userService;

  public AuthController(UserService userService) {
    this.userService = userService;
  }

  @PostMapping("/sign-up")
  public void signUpUser() {
  }

  @PostMapping("/sign-in")
  public ResponseEntity<Response> signInUser(@RequestBody @Valid CreateUserDTO payload,
                                             UriComponentsBuilder uriComponentsBuilder) {
    var user = this.userService.signUpUser(payload);
    var uri = uriComponentsBuilder.path("/auth/profile/{id}").buildAndExpand(user.id()).toUri();

    return ResponseEntity.created(uri).body(
        new Response(true, user)
    );
  }
}
