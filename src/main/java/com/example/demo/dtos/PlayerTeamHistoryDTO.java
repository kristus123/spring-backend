package com.example.demo.dtos;

import com.example.demo.models.PlayerHistoryModel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Date;

@Getter
@Setter
public class PlayerTeamHistoryDTO {
    LocalDate teamDateFrom;
    LocalDate teamDateTo;
    Integer teamId;

    public PlayerTeamHistoryDTO(LocalDate teamDateFrom, LocalDate teamDateTo, Integer teamId) {
        this.teamDateFrom = teamDateFrom;
        this.teamDateTo = teamDateTo;
        this.teamId = teamId;
    }
}
