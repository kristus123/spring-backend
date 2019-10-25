package com.example.demo.models;


import com.example.demo.enums.PositionType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
@Entity
@Table(name="MATCH_POSITION")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MatchPositionModel {

    @EmbeddedId
    MatchPositionId id;

    @ManyToOne
    @MapsId("player_id")
    @JoinColumn(name = "player_id")
    private PlayerModel player;

    @ManyToOne
    @MapsId("match_id")
    @JoinColumn(name = "match_id")
    private MatchModel match;

    @Column(name = "position")
    private String position;

    public MatchPositionModel(PlayerModel player, MatchModel match, String position) {
        this.player = player;
        this.match = match;
        this.position = position;
    }


}
