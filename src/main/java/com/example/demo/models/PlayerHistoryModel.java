package com.example.demo.models;

import com.example.demo.models.PlayerModel;

import javax.persistence.*;
import javax.validation.constraints.Size;

@Entity(name = "PLAYER_HISTORY")
@Table(name = "PLAYER_HISTORY")
@AttributeOverride(name="playerId", column = @Column(name="playerr_id", insertable = false, updatable = false))
public class PlayerHistoryModel extends PlayerModel {
}
