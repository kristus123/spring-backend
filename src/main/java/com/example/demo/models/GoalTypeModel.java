package com.example.demo.models;


import com.example.demo.enums.GoalType;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;


//
//
// DENNE MODELLEN KOMMER IKKE TIL Å BLI BRUKT
// Istedenfor bruker vi bare enums i MatchGoalModel, cleaner, easier and better
//

@Entity
@Table(name="GOAL_TYPE")
@Getter
@Setter
public class GoalTypeModel {


    //
//
// DENNE MODELLEN KOMMER IKKE TIL Å BLI BRUKT
// Istedenfor bruker vi bare enums i MatchGoalModel, cleaner, easier and better
//
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    @Column(name = "goal_type_id")
    private Integer goalTypeId;

    //
//
// DENNE MODELLEN KOMMER IKKE TIL Å BLI BRUKT
// Istedenfor bruker vi bare enums i MatchGoalModel, cleaner, easier and better
//
    //
//
// DENNE MODELLEN KOMMER IKKE TIL Å BLI BRUKT
// Istedenfor bruker vi bare enums i MatchGoalModel, cleaner, easier and better
//
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private GoalType type;

//
//
// DENNE MODELLEN KOMMER IKKE TIL Å BLI BRUKT
// Istedenfor bruker vi bare enums i MatchGoalModel, cleaner, easier and better
//

    //
//
// DENNE MODELLEN KOMMER IKKE TIL Å BLI BRUKT
// Istedenfor bruker vi bare enums i MatchGoalModel, cleaner, easier and better
//
    public GoalTypeModel() {
    }

//
//
// DENNE MODELLEN KOMMER IKKE TIL Å BLI BRUKT
// Istedenfor bruker vi bare enums i MatchGoalModel, cleaner, easier and better
//

    public GoalTypeModel(GoalType type) {
        this.type = type;
    }
}
