package com.example.demo.models;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
@Entity
@IdClass (MatchPositionId.class)
@Table(name="MATCH_POSITION")
@Getter
@Setter
public class MatchPositionModel {

    @Id
    @OneToOne (cascade = CascadeType.ALL)
    @JoinColumn(name = "player_id", referencedColumnName = "player_id")
    private PlayerModel player;

    @Id
    @OneToOne (cascade = CascadeType.ALL)
    @JoinColumn(name = "match_id", referencedColumnName = "match_id")
    private MatchModel match;

    private String position; // TODO PANDA: Integer? String?

    public MatchPositionModel() {
    }

    public MatchPositionModel(PlayerModel player, MatchModel match, String position) {
        this.player = player;
        this.match = match;
        this.position = position;
    }
}
