package com.eduforum.api.forum_api.infra.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.eduforum.api.forum_api.domain.user.model.User;
import com.eduforum.api.forum_api.infra.errors.UnauthorizedException;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
public class TokenService {

  //  @Value("${api.security.secret-jwt}")
  private String JWT_SECRET = "holamundo";
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

  public String getSubject(String token) {
    if (token == null) {
      throw new UnauthorizedException("token is missing");
    }

    try {
      Algorithm algorithm = Algorithm.HMAC256(JWT_SECRET);
      JWTVerifier verifier = JWT.require(algorithm)
          .withIssuer("eduforum")
          .build();
      decodedJWT = verifier.verify(token);
    } catch (JWTVerificationException exception) {
      throw new UnauthorizedException(exception.getMessage());
    }

    if (decodedJWT.getSubject() == null) {
      throw new UnauthorizedException("Verifier invalid");
    }

    return decodedJWT.getSubject();
  }
}

