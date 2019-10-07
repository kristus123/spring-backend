package com.example.demo.models;

import com.example.demo.enums.UserRole;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

import javax.persistence.*;
import javax.validation.constraints.Size;

@Entity
@Table(name="PLAYER")
@Getter
@Setter
public class PlayerModel {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer player_id;

  @Size(min = 4, max = 255, message = "Minimum player name length: 4 characters")
  @Column(unique = true, nullable = false)
  private String playername;

  @Column(unique = true, nullable = false)
  private String email;



  @ElementCollection(fetch = FetchType.EAGER)
  List<UserRole> roles;

  public Integer getId() {
    return player_id;
  }

  public void setId(Integer player_id) {
    this.player_id = player_id;
  }

  public List<UserRole> getRoles() {
    return roles;
  }

  public void setRoles(List<UserRole> roles) {
    this.roles = roles;
  }

}
