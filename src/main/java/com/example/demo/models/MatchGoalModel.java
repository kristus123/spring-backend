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
    private Integer goal_id;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "player_id", referencedColumnName = "player_id")
    private PlayerModel player;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "goal_type_id", referencedColumnName = "goal_type_id")
    private GoalTypeModel goalType;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "match_id", referencedColumnName = "match_id")
    private MatchModel match;

    private String description;

    public MatchGoalModel() {
    }

    public MatchGoalModel(PlayerModel player, GoalTypeModel goalType, MatchModel match, String description) {
        this.player = player;
        this.goalType = goalType;
        this.match = match;
        this.description = description;
    }
}
