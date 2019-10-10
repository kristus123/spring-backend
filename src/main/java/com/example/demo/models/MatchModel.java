package com.example.demo.models;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Table(name="MATCH_MODEL")
@Getter
@Setter
public class MatchModel {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    @Column(name = "match_id")
    private Integer matchId;

    private Date matchDate;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "home_team_id", referencedColumnName = "team_id")
    private TeamModel homeTeam;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "away_team_id", referencedColumnName = "team_id")
    private TeamModel awayTeam;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "season_id", referencedColumnName = "season_id")
    private SeasonModel season;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "location_id", referencedColumnName = "location_id")
    private LocationModel location;

    public MatchModel() {
    }

    public MatchModel(Date matchDate, TeamModel homeTeam, TeamModel awayTeam, SeasonModel season, LocationModel location) {
        this.matchDate = matchDate;
        this.homeTeam = homeTeam;
        this.awayTeam = awayTeam;
        this.season = season;
        this.location = location;
    }
}

