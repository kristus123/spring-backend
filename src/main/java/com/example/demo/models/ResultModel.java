package com.example.demo.models;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table (name = "RESULT_MODEL")
@Getter
@Setter
@NoArgsConstructor
public class ResultModel implements Serializable {

    @Id
    @Column (name = "match_id")
    private Integer matchId; // NB!! Should not be auto-generated! Foreign-key to MatchModel.matchId

    @Cascade(org.hibernate.annotations.CascadeType.SAVE_UPDATE)
    @OneToOne//(cascade = CascadeType.ALL)
    @JoinColumn (name = "match_id", referencedColumnName = "match_id")
    @MapsId
    private MatchModel match;

    @Cascade(org.hibernate.annotations.CascadeType.SAVE_UPDATE)
    @OneToOne//(cascade = CascadeType.ALL)
    @JoinColumn (name = "team_id", referencedColumnName = "team_id")
    private TeamModel team;

    @Column (nullable = false)
    private Integer score;

    @Column (nullable = false)
    private String result;

    public ResultModel(MatchModel match, TeamModel team, Integer score, String result) {
        this.match = match;
        this.team = team;
        this.score = score;
        this.result = result;
    }
}
