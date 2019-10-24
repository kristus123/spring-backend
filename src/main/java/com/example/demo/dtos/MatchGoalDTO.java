package com.example.demo.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MatchGoalDTO {

    private Integer playerId;
    private Integer goalTypeId;
    private Integer matchId;
    private String description;

    // MANDATORY
    public MatchGoalDTO(Integer playerId, Integer goalTypeId, Integer matchId) {
        this.playerId = playerId;
        this.goalTypeId = goalTypeId;
        this.matchId = matchId;
    }

}