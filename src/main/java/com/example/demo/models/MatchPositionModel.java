package com.example.demo.models;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
@Entity
@IdClass (AccountId.class)

@Table(name="MATCH_POSITION")
@Getter
@Setter
public class MatchPositionModel {

@Id
    @OneToOne (cascade = CascadeType.ALL)
    @JoinColumn(name = "player_id", referencedColumnName = "player_id")
    private int player_id;

    @Id
    @OneToOne (cascade = CascadeType.ALL)
    @JoinColumn(name = "match_id", referencedColumnName = "match_id")
    private int match_id;

}
