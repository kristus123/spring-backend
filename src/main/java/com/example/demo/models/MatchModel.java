package com.example.demo.models;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name="match")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MatchModel {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    @Column(name = "match_id")
    private Integer matchId;

    private LocalDate matchDate;

    @Cascade(org.hibernate.annotations.CascadeType.SAVE_UPDATE)
    @OneToOne//(cascade = CascadeType.MERGE)
    @JoinColumn(name = "home_team_id", referencedColumnName = "team_id")
    private TeamModel homeTeam;

    @Cascade(org.hibernate.annotations.CascadeType.SAVE_UPDATE)
    @OneToOne//(cascade = CascadeType.MERGE)
    @JoinColumn(name = "away_team_id", referencedColumnName = "team_id")
    private TeamModel awayTeam;

    @Cascade(org.hibernate.annotations.CascadeType.SAVE_UPDATE)
    @OneToOne//(cascade = CascadeType.MERGE)
    @JoinColumn(name = "season_id", referencedColumnName = "season_id")
    private SeasonModel season;

    @Cascade(org.hibernate.annotations.CascadeType.SAVE_UPDATE)
    @OneToOne//(cascade = CascadeType.MERGE)
    @JoinColumn(name = "location_id", referencedColumnName = "location_id")
    private LocationModel location;


    // Match position properties
    @JsonIgnore
    @OneToMany(mappedBy = "match")
    private Set<MatchGoalModel> positions;

    /*
    @JsonIgnore
    @ManyToMany
    @JoinTable(
            name = "MATCH_POSITION",
            joinColumns = { @JoinColumn(name = "match_id") },
            inverseJoinColumns = { @JoinColumn(name = "player_id") }
    )
    private Set<PlayerModel> positions = new HashSet<>();
     */



    public MatchModel(LocalDate matchDate, TeamModel homeTeam, TeamModel awayTeam, SeasonModel season, LocationModel location) {
        this.matchDate = matchDate;
        this.homeTeam = homeTeam;
        this.awayTeam = awayTeam;
        this.season = season;
        this.location = location;
    }
}

