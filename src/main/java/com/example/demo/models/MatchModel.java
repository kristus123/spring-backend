package com.example.demo.models;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name="MATCH")
@Getter
@Setter
public class MatchModel {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    @Column(name = "match_id")
    private Integer matchId;

    private LocalDate matchDate;

    @OneToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "home_team_id", referencedColumnName = "team_id")
    private TeamModel homeTeam;

    @OneToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "away_team_id", referencedColumnName = "team_id")
    private TeamModel awayTeam;

    @OneToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "season_id", referencedColumnName = "season_id")
    private SeasonModel season;

    @OneToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "location_id", referencedColumnName = "location_id")
    private LocationModel location;

    public MatchModel() {
    }

    public MatchModel(LocalDate matchDate, TeamModel homeTeam, TeamModel awayTeam, SeasonModel season, LocationModel location) {
        this.matchDate = matchDate;
        this.homeTeam = homeTeam;
        this.awayTeam = awayTeam;
        this.season = season;
        this.location = location;
    }
}

