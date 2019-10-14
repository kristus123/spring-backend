package com.example.demo.models;


import com.example.demo.enums.GoalType;
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
    @Column(name = "goal_id")
    private Integer goalId;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "player_id", referencedColumnName = "player_id")
    private PlayerModel player;

    @Column
    @Enumerated(EnumType.STRING)
    private GoalType goalType;

    @OneToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "match_id", referencedColumnName = "match_id")
    private MatchModel match;

    private String description;

    public MatchGoalModel() {
    }

    public MatchGoalModel(PlayerModel player, GoalType goalType, MatchModel match, String description) {
        this.player = player;
        this.goalType = goalType;
        this.match = match;
        this.description = description;
    }
}
