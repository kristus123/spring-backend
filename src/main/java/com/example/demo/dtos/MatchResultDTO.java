package com.example.demo.dtos;


import com.example.demo.models.MatchModel;
import com.example.demo.models.ResultModel;
import com.example.demo.models.TeamModel;
import com.example.demo.services.MatchService;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;

@Getter
@Setter
@NoArgsConstructor
public class MatchResultDTO{

    @Autowired
    MatchService matchService;

    @Autowired
    MatchService homeTeamService;

    @Autowired
    MatchService awayTeamService;

    private MatchModel matchModel;
    private TeamModel homeTeamModel;
    private TeamModel awayTeamModel;
    private String result;

    //Constructor
    public MatchResultDTO(MatchModel matchModel, TeamModel homeTeamModel, TeamModel awayTeamModel, String result) {
        this.matchModel = matchModel;
        this.homeTeamModel = homeTeamModel;
        this.awayTeamModel = awayTeamModel;
        this.result = result;

    }
}
