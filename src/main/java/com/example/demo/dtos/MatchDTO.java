package com.example.demo.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MatchDTO {

    private LocalDate matchDate;
    private Integer homeTeamId;
    private Integer awayTeamId;
    private Integer seasonId;
    private Integer locationId;

}