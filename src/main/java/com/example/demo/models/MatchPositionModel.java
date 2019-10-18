package com.example.demo.models;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
@Entity
@IdClass (MatchPositionId.class)
@Table(name="MATCH_POSITION_blabla")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MatchPositionModel {

    @Id
    @OneToOne (cascade = CascadeType.ALL)
    @JoinColumn(name = "player_id", referencedColumnName = "player_id")
    private PlayerModel player;

    @Id
    @OneToOne (cascade = CascadeType.ALL)
    @JoinColumn(name = "match_id", referencedColumnName = "match_id")
    private MatchModel match;

    private String position;


}
