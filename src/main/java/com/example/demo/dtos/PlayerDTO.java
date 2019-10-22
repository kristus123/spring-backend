package com.example.demo.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PlayerDTO {
    private Integer playerId;
    private LocalDate teamDateFrom;
    private LocalDate teamDateTo;
    private String playername;
    private Integer personId;
    private Integer teamId;
    private String normalPosition;
    private String playerNumber;
    private String imageUrl;
}
