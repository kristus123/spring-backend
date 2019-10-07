package com.example.demo.models;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name="MATCH_GOAL")
@Getter
@Setter
public class MatchGoalModel {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Integer match_goal_id;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "player_id", referencedColumnName = "player_id")
    private int player_id;
}
