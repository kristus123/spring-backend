package com.example.demo.models;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name="RESULT_MODEL")
@Getter
@Setter
public class ResultModel implements Serializable {

    @Id
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "match_id", referencedColumnName = "match_id")
    private MatchModel match;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "team_id", referencedColumnName = "team_id")
    private TeamModel team;

    @Column(nullable = false)
    private Integer score;

    @Column(nullable = false)
    private String result;

    public ResultModel() {}
    public ResultModel(MatchModel match, TeamModel team, Integer score, String result) {
        this.match = match;
        this.team = team;
        this.score = score;
        this.result = result;
    }
}
