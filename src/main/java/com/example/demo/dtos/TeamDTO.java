package com.example.demo.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TeamDTO {

    private Integer teamId;
    private Integer associationId;
    private Integer coachId;
    private Integer ownerId;
    private Integer locationId;

}
