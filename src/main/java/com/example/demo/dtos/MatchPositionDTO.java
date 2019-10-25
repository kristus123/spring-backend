package com.example.demo.dtos;

import com.example.demo.enums.PositionType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MatchPositionDTO {

    private Integer playerId;
    private Integer matchId;
    private String position;

    public MatchPositionDTO(Integer playerId, Integer matchId) {
        this.playerId = playerId;
        this.matchId = matchId;
    }

}