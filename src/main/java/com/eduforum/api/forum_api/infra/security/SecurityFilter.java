package com.eduforum.api.forum_api.infra.security;

import com.eduforum.api.forum_api.domain.user.repository.UserRepository;
import com.eduforum.api.forum_api.infra.errors.UnauthorizedException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

import java.io.IOException;

@Component
public class SecurityFilter extends OncePerRequestFilter {
  private final TokenService tokenService;
  private final UserRepository userRepository;
  private final HandlerExceptionResolver handlerExceptionResolver;

  @Autowired
  public SecurityFilter(TokenService tokenService, UserRepository userRepository, HandlerExceptionResolver handlerExceptionResolver) {
    this.tokenService = tokenService;
    this.userRepository = userRepository;
    this.handlerExceptionResolver = handlerExceptionResolver;

  }

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
    var authHeader = request.getHeader("Authorization");
    try {
      if (authHeader != null) {
        var token = authHeader.replace("Bearer ", "");
        var emailUser = tokenService.getSubject(token);
        if (emailUser != null) {
          var user = this.userRepository.findByEmail(emailUser);
          var authentication = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
          SecurityContextHolder.getContext().setAuthentication(authentication);
        }
      }
      filterChain.doFilter(request, response);
    } catch (RuntimeException e) {
      handlerExceptionResolver.resolveException(request, response, null, new UnauthorizedException(e.getMessage()));
    }
  }
}
