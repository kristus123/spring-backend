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

  @OneToOne(cascade = CascadeType.ALL)
  @JoinColumn(name = "person_id", referencedColumnName = "person_id")
  private PersonModel person;

  @OneToOne(cascade = CascadeType.ALL)
  @JoinColumn(name = "team_id", referencedColumnName = "team_id")
  private TeamModel team;

  private String normal_position;

  private Integer player_number;


  public PlayerModel() {
  }

  public PlayerModel(@Size(min = 4, max = 255, message = "Minimum player name length: 4 characters") String playername, PersonModel person, TeamModel team, String normal_position, Integer player_number) {
    this.playername = playername;
    this.person = person;
    this.team = team;
    this.normal_position = normal_position;
    this.player_number = player_number;
  }
}
