package com.eduforum.api.forum_api.domain.user.service;

import com.eduforum.api.forum_api.domain.user.dtos.AuthenticateUserDTO;
import com.eduforum.api.forum_api.domain.user.dtos.CreateUserDTO;
import com.eduforum.api.forum_api.domain.user.dtos.GetProfile;
import com.eduforum.api.forum_api.domain.user.dtos.GetUserWithProfile;
import com.eduforum.api.forum_api.domain.user.model.Profile;
import com.eduforum.api.forum_api.domain.user.model.User;
import com.eduforum.api.forum_api.domain.user.repository.ProfileRepository;
import com.eduforum.api.forum_api.domain.user.repository.RoleRepository;
import com.eduforum.api.forum_api.domain.user.repository.UserRepository;
import com.eduforum.api.forum_api.infra.errors.BadRequestException;
import com.eduforum.api.forum_api.infra.errors.ForbiddenException;
import com.eduforum.api.forum_api.infra.errors.ValidateException;
import com.eduforum.api.forum_api.infra.security.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

  private final UserRepository userRepository;
  private final ProfileRepository profileRepository;
  private final AuthenticationManager authenticationManager;
  private final RoleRepository roleRepository;
  private final TokenService tokenService;
  private final BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

  @Autowired
  public UserService(UserRepository userRepository, ProfileRepository profileRepository
      , AuthenticationManager authenticationManager, RoleRepository roleRepository, TokenService tokenService) {
    this.userRepository = userRepository;
    this.profileRepository = profileRepository;
    this.authenticationManager = authenticationManager;
    this.roleRepository = roleRepository;
    this.tokenService = tokenService;

  }

  public GetUserWithProfile signUpUser(CreateUserDTO payload) {
    var exitsEmail = this.userRepository.existsByEmail(payload.email());
    if (exitsEmail) {
      throw new BadRequestException("email (" + payload.email() + ") already exists");
    }
    var role = this.roleRepository.findRoleByName("ROLE_USER")
        .orElseThrow(() -> new ValidateException("role not found"));

    var passwordEncode = bCryptPasswordEncoder.encode(payload.password());
    User user = this.userRepository.save(new User(payload.email(), passwordEncode, role));
    Profile profile = this.profileRepository.save(new Profile(payload.profile(), user));
    return new GetUserWithProfile(user.getId(), user.getEmail(), new GetProfile(profile));
  }

  public User findUserByEmail(String email) {
    return this.userRepository.findUserByEmail(email).orElseThrow(() ->
        new ValidateException("user with email (" + email + ") not found")
    );
  }

  public String signIn(AuthenticateUserDTO authUser) {
    Authentication authenticateUser = null;
    Authentication token = new UsernamePasswordAuthenticationToken(
        authUser.email(),
        authUser.password()
    );
    try {
      authenticateUser = authenticationManager.authenticate(token);
    } catch (RuntimeException e) {
      throw new ForbiddenException("email or password incorrect");
    }
    return tokenService.generatedToken((UserDetails) authenticateUser.getPrincipal());
  }
}
