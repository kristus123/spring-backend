package com.example.demo.models;

import com.example.demo.dtos.PlayerDTO;
import com.example.demo.interfaces.LivingHuman;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.vladmihalcea.hibernate.type.range.PostgreSQLRangeType;
import com.vladmihalcea.hibernate.type.range.Range;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.TypeDef;
import org.hibernate.envers.Audited;

import org.hibernate.envers.RelationTargetAuditMode;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name="PLAYER")
@Getter
@Setter
@NoArgsConstructor
@Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
@TypeDef(typeClass = PostgreSQLRangeType.class, defaultForType = Range.class) //Handling ranges the postgres way
@EntityListeners({AuditingEntityListener.class})
public class PlayerModel implements LivingHuman {
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "player_id")
    private Integer playerId;


    @Size(min = 4, max = 255, message = "Minimum player name length: 4 characters")
    @Column(nullable = false)
    private String playername;

    @Column(name = "team_date_from")
    private LocalDate teamDateFrom;

    @Column(name = "team_date_to")
    private LocalDate teamDateTo;

    @Column(name ="image_url")
    private String imageUrl;

    @Cascade(org.hibernate.annotations.CascadeType.SAVE_UPDATE)
    @OneToOne//(cascade = CascadeType.MERGE,  orphanRemoval = true)
    @JoinColumn(name = "person_id", referencedColumnName = "person_id")
    private PersonModel person;

    @Cascade(org.hibernate.annotations.CascadeType.SAVE_UPDATE)
    @OneToOne//(cascade = CascadeType.MERGE,  orphanRemoval = true)
    @JoinColumn(name = "team_id", referencedColumnName = "team_id")
    private TeamModel team;

    @Column(nullable = true)
    private String normalPosition;

    @Column(nullable = true)
    private String playerNumber;

    @JsonIgnore
    @Column(name = "active")
    private boolean active = true;


    // Watchlist properties
    @JsonIgnore
    @ManyToMany(mappedBy = "players")
    private Set<UserModel> users = new HashSet<>();


    // Match position properties
    @JsonIgnore
    @OneToMany(mappedBy = "player")
    private Set<MatchGoalModel> positions;



    public PlayerModel(PersonModel person, TeamModel team, String normalPosition, String playerNumber, LocalDate teamDateFrom, LocalDate teamDateTo, String playername) {
        this.person = person;
        this.team = team;
        this.normalPosition = normalPosition;
        this.playerNumber = playerNumber;
        this.teamDateFrom = teamDateFrom;
        this.teamDateTo = teamDateTo;
        this.playername = playername;
    }

    public PlayerModel(PersonModel person, TeamModel team, String normalPosition, String playerNumber, String playername) {

        this.person = person;
        this.team = team;
        this.normalPosition = normalPosition;
        this.playerNumber = playerNumber;
        this.playername = playername;
    }

    public PlayerModel(PersonModel person, TeamModel team, String normalPosition, String playerNumber, String playername, String imageUrl) {

        this.person = person;
        this.team = team;
        this.normalPosition = normalPosition;
        this.playerNumber = playerNumber;
        this.playername = playername;
        this.imageUrl = imageUrl;
    }

  public PlayerModel(PersonModel person) {
        this.person = person;
        this.playername = person.getFirstName() + " " + person.getLastName();

    }


    public PlayerModel(PlayerDTO player) {
        this.teamDateFrom = player.getTeamDateFrom();
        this.teamDateTo = player.getTeamDateTo();
        this.normalPosition = player.getNormalPosition();
        this.playerNumber = player.getPlayerNumber();
        this.playername = player.getPlayername();
        this.imageUrl = player.getImageUrl();
  }

  @Override
  public String toString() {
    return person.getFirstName() + " spiller for " + team.getAssociation().getName() + " : posisjonen hans er " + normalPosition + " og spillernummer er " + playerNumber;
  }

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
