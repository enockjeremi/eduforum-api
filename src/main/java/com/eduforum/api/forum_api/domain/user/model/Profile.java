package com.eduforum.api.forum_api.domain.user.model;

import com.eduforum.api.forum_api.domain.user.dtos.CreateProfileDTO;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "profiles")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "Id")
public class Profile {

  @jakarta.persistence.Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long Id;

  private String name;
  private String lastName;
  private String country;

  @OneToOne
  private User user;

  public Profile(CreateProfileDTO profile, User user) {
    this.name = profile.name();
    this.user = user;
    this.lastName = profile.lastName();
    this.country = profile.country();
  }
}
