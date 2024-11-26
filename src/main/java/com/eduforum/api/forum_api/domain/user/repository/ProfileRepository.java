package com.eduforum.api.forum_api.domain.user.repository;

import com.eduforum.api.forum_api.domain.user.model.Profile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProfileRepository extends JpaRepository<Profile, Long> {
}
