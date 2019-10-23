package com.example.demo.models;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.envers.RevisionType;

@Getter
@Setter
public class PlayerHistoryModel {
    private final PlayerModel playerModel;
    private final RevisionType revisionType;

    public PlayerHistoryModel(PlayerModel playerModel, RevisionType revisionType) {
        this.playerModel = playerModel;
        this.revisionType = revisionType;
    }

}
