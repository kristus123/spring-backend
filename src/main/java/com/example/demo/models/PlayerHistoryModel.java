package com.example.demo.models;

import com.example.demo.models.PlayerModel;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.envers.RevisionType;

import javax.persistence.*;
import javax.validation.constraints.Size;

@Getter
@Setter
public class PlayerHistoryModel {
    private final PlayerModel playerModel;
    //private final Number revision;
    private final RevisionType revisionType;

    public PlayerHistoryModel(PlayerModel playerModel, RevisionType revisionType) {
        this.playerModel = playerModel;
        //this.revision = revision;
        this.revisionType = revisionType;
    }

}
