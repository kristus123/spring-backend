package com.example.demo.models;

import com.example.demo.interfaces.LivingHuman;
import com.vladmihalcea.hibernate.type.range.PostgreSQLRangeType;
import com.vladmihalcea.hibernate.type.range.Range;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.TypeDef;
import org.hibernate.envers.AuditReader;
import org.hibernate.envers.AuditReaderFactory;
import org.hibernate.envers.Audited;

import org.hibernate.envers.RelationTargetAuditMode;
import org.hibernate.envers.query.AuditQuery;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.transaction.Transactional;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name="PLAYER")
@Getter
@Setter
@Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
@TypeDef(typeClass = PostgreSQLRangeType.class, defaultForType = Range.class) //Handling ranges the postgres way
@EntityListeners({AuditingEntityListener.class})
//@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class PlayerModel implements LivingHuman {
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "player_id")
    private Integer playerId;

    @Size(min = 4, max = 255, message = "Minimum player name length: 4 characters")
    @Column(nullable = false)
    private String playername;

    // Range is not supported by Envers (audit)
    // Maybe just add two localDates? From data and to date
//    @Column(name="teamRange" , columnDefinition = "daterange")
//    private Range<LocalDate> sysTime;

    @Column(name = "team_date_from")
    private LocalDate teamDateFrom;

    @Column(name = "team_date_to")
    private LocalDate teamDateTo;

    @OneToOne(cascade = CascadeType.MERGE,  orphanRemoval = true)
    @JoinColumn(name = "person_id", referencedColumnName = "person_id")
    private PersonModel person;


    @OneToOne(cascade = CascadeType.MERGE,  orphanRemoval = true)
    @JoinColumn(name = "team_id", referencedColumnName = "team_id")
    private TeamModel team;

    private String normalPosition;

    private String playerNumber;

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
