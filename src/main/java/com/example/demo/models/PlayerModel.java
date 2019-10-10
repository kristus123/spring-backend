package com.example.demo.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Size;

@Entity
@Table(name="PLAYER")
@Getter
@Setter
public class PlayerModel {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "player_id")
  private Integer playerId;


  @OneToOne(cascade = CascadeType.ALL)
  @JoinColumn(name = "person_id", referencedColumnName = "person_id")
  private PersonModel person;

  @OneToOne(cascade = CascadeType.ALL)
  @JoinColumn(name = "team_id", referencedColumnName = "team_id")
  private TeamModel team;

  private String normalPosition;

  private String playerNumber;


  public PlayerModel() {
  }

  public PlayerModel(PersonModel person, TeamModel team, String normalPosition, String playerNumber) {
    this.person = person;
    this.team = team;
    this.normalPosition = normalPosition;
    this.playerNumber = playerNumber;
  }

  @Override
  public String toString() {
    return person.getFirstName() + " spiller for " + team.getAssociation().getName() + " : posisjonen hans er " + normalPosition + " og spillernummer er " + playerNumber;
  }
}
