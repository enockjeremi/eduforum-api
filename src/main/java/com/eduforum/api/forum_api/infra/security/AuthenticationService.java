package com.eduforum.api.forum_api.infra.security;

import com.eduforum.api.forum_api.domain.user.dtos.AuthenticateUserDTO;
import com.eduforum.api.forum_api.domain.user.model.User;
import com.eduforum.api.forum_api.domain.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService implements UserDetailsService {

  private final UserRepository userRepository;

  @Autowired
  public AuthenticationService(UserRepository userRepository) {
    this.userRepository = userRepository;

  }

  @Override
  public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
    User user = this.userRepository.findUserByEmail(email)
        .orElseThrow(() -> new UsernameNotFoundException("User not found"));

    return new org.springframework.security.core.userdetails.User(
        user.getUsername(),
        user.getPassword(),
        user.getRoles().stream()
            .map(role -> new SimpleGrantedAuthority(role.getName()))
            .toList()
    );
  }

}
