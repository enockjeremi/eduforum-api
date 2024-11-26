package com.eduforum.api.forum_api.domain.user.service;

import com.eduforum.api.forum_api.domain.user.dtos.CreateUserDTO;
import com.eduforum.api.forum_api.domain.user.dtos.GetProfile;
import com.eduforum.api.forum_api.domain.user.dtos.GetUser;
import com.eduforum.api.forum_api.domain.user.model.Profile;
import com.eduforum.api.forum_api.domain.user.model.User;
import com.eduforum.api.forum_api.domain.user.repository.ProfileRepository;
import com.eduforum.api.forum_api.domain.user.repository.UserRepository;
import com.eduforum.api.forum_api.infra.errors.ValidateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

  private final UserRepository userRepository;
  private final ProfileRepository profileRepository;

  private final BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

  @Autowired
  public UserService(UserRepository userRepository, ProfileRepository profileRepository) {
    this.userRepository = userRepository;
    this.profileRepository = profileRepository;
  }

  public GetUser signUpUser(CreateUserDTO payload) {
    var exitsEmail = this.userRepository.existsByEmail(payload.email());
    if (exitsEmail) {
      throw new ValidateException("email (" + payload.email() + ") already exists");
    }
    var passwordEncode = bCryptPasswordEncoder.encode(payload.password());
    User user = this.userRepository.save(new User(payload.email(), passwordEncode));
    Profile profile = this.profileRepository.save(new Profile(payload.profile(), user));
    return new GetUser(user.getId(), user.getEmail(), new GetProfile(profile));
  }
}
