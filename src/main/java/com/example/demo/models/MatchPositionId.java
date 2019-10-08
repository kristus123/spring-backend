package com.example.demo.models;


import java.io.Serializable;

public class MatchPositionId implements Serializable {

    private PlayerModel player;

    private MatchModel match;

    public MatchPositionId(PlayerModel player, MatchModel match) {
        this.player = player;
        this.match = match;
    }

}
