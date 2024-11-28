package com.eduforum.api.forum_api.domain.user.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "roles")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "Id")
public class Role {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long Id;
  private String name;

}
