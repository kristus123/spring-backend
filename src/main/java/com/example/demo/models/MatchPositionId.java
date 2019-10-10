package com.example.demo.models;


import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
@Getter
@Setter
public class MatchPositionId implements Serializable {

    private PlayerModel player;

    private MatchModel match;

    public MatchPositionId() {

    }

    public MatchPositionId(PlayerModel player, MatchModel match) {
        this.player = player;
        this.match = match;
    }

}
