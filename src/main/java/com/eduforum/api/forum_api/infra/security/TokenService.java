package com.eduforum.api.forum_api.infra.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.eduforum.api.forum_api.domain.user.model.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
public class TokenService {

//  @Value("${api.security.secret-jwt}")
  private String JWT_SECRET= "holamundo";
  private DecodedJWT decodedJWT;

  public String generatedToken(User user) {
    try {
      Algorithm algorithm = Algorithm.HMAC256(JWT_SECRET);
      return JWT.create()
          .withIssuer("eduforum")
          .withSubject(user.getEmail())
          .withClaim("id", user.getId())
          .withClaim("role", user.getRole())
          .withExpiresAt(generatedDateExpired())
          .sign(algorithm);
    } catch (JWTCreationException exception) {
      throw new RuntimeException();
    }
  }

  public Instant generatedDateExpired() {
    return LocalDateTime.now().plusHours(4).toInstant(ZoneOffset.of("-05:00"));
  }
}
