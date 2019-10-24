package com.example.demo.dtos;

import com.example.demo.models.GoalTypeModel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MatchGoalDTO {

    private Integer playerId;
    private String goalType;
    private Integer matchId;
    private String description;

    // MANDATORY
    public MatchGoalDTO(Integer playerId, String goalType, Integer matchId) {
        this.playerId = playerId;
        this.goalType = goalType;
        this.matchId = matchId;
    }

}