package com.example.demo.models;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name="MATCH")
@Getter
@Setter
public class MatchModel {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Integer match_id;

    private Date match_date;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "home_team_id", referencedColumnName = "team_id")
    private int home_team_id;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "away_team_id", referencedColumnName = "team_id")
    private int away_team_id;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "season_id", referencedColumnName = "season_id")
    private int season_id;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "location_id", referencedColumnName = "location_id")
    private int location_id;
}

