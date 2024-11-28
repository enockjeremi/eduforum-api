package com.eduforum.api.forum_api.domain.user.model;

import com.eduforum.api.forum_api.domain.topic.model.Topic;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.*;

@Entity
@Table(name = "users")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "Id")
public class User implements UserDetails {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long Id;

  private String email;
  private String password;
  @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
  @JoinTable(
      name = "user_roles",
      joinColumns = @JoinColumn(name = "user_id"),
      inverseJoinColumns = @JoinColumn(name = "role_id")
  )
  private Set<Role> roles = new HashSet<>();

  @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
  private Profile profile;

  @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
  private List<Topic> topic;

  public User(String email, String password, Role roles) {
    this.setRoles(Set.of(roles));
    this.email = email;
    this.password = password;
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    Set<Role> roles = this.getRoles();
    List<SimpleGrantedAuthority> authorities = new ArrayList<>();
    for (Role role : roles) {
      authorities.add(new SimpleGrantedAuthority(role.getName()));
    }
    return authorities;
  }
  @Override
  public String getUsername() {
    return this.email;
  }

  @Override
  public boolean isAccountNonExpired() {
    return UserDetails.super.isAccountNonExpired();
  }

  @Override
  public boolean isAccountNonLocked() {
    return UserDetails.super.isAccountNonLocked();
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return UserDetails.super.isCredentialsNonExpired();
  }

  @Override
  public boolean isEnabled() {
    return UserDetails.super.isEnabled();
  }
}
