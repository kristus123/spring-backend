package com.example.demo.dtos;

import com.example.demo.models.TeamModel;
import com.example.demo.services.TeamService;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;

@Getter
@Setter
@NoArgsConstructor
public class TeamDTO {

    @Autowired
    TeamService teamService;

    private TeamModel teamModel;

    public TeamDTO(TeamModel teamModel) {
        teamModel.getTeamId();
        this.teamModel = teamModel;
    }
}
