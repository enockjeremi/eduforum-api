package com.eduforum.api.forum_api.domain.user.repository;

import com.eduforum.api.forum_api.domain.user.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

public interface UserRepository extends JpaRepository<User, Long> {
  UserDetails findByEmail(String email);

  Boolean existsByEmail(String email);
}
