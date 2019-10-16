package com.example.demo.models;

import com.example.demo.interfaces.LivingHuman;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name="PLAYER")
@Getter
@Setter
public class PlayerModel implements LivingHuman {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "player_id")
    private Integer playerId;


    @Size(min = 4, max = 255, message = "Minimum player name length: 4 characters")
    @Column(nullable = false)
    private String playername;


    @OneToOne(cascade = CascadeType.MERGE,  orphanRemoval = true)
    @JoinColumn(name = "person_id", referencedColumnName = "person_id")
    private PersonModel person;


    @OneToOne(cascade = CascadeType.MERGE,  orphanRemoval = true)
    @JoinColumn(name = "team_id", referencedColumnName = "team_id")
    private TeamModel team;

    private String normalPosition;

    private String playerNumber;


    // Watchlist properties

    @JsonIgnore
    @ManyToMany(mappedBy = "players")
    private Set<UserModel> users = new HashSet<>();


    public PlayerModel() {
    }




  public PlayerModel(PersonModel person, TeamModel team, String normalPosition, String playerNumber, String playername) {

    this.person = person;
    this.team = team;
    this.normalPosition = normalPosition;
    this.playerNumber = playerNumber;
    this.playername = playername;
  }

  public PlayerModel(PersonModel person) {
        this.person = person;
        this.playername = person.getFirstName() + " " + person.getLastName();

  }

  /*
  @Override
  public String toString() {
    return person.getFirstName() + " spiller for " + team.getAssociation().getName() + " : posisjonen hans er " + normalPosition + " og spillernummer er " + playerNumber;
  }
*/
    // TODO PANDA: for testing purposes
    public PlayerModel(Integer id, String name) {
        this.playerId = id;
        this.playername = name;
    }


    @Override
    public PersonModel getPersonObject() {
        return null;
    }
}
