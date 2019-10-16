package com.example.demo.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PlayerDTO {

    private Integer playerId;

    private Integer personId;

    private Integer teamId;

    private String normalPosition;

    private String playerNumber;
}
