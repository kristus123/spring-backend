package com.example.demo.assembler;

import com.example.demo.controllers.commonControllers.CommonLocationController;
import com.example.demo.controllers.commonControllers.CommonMatchController;
import com.example.demo.controllers.commonControllers.CommonSeasonController;
import com.example.demo.controllers.commonControllers.CommonTeamController;
import com.example.demo.models.MatchModel;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.*;

import org.springframework.hateoas.Resource;
import org.springframework.hateoas.ResourceAssembler;
import org.springframework.stereotype.Component;

@Component
public class MatchResourceAssembler implements ResourceAssembler<MatchModel, Resource<MatchModel>> {
    @Override
    public Resource<MatchModel> toResource(MatchModel match) {
        return new Resource<>(match,
                linkTo(methodOn(CommonMatchController.class).getMatch(match.getMatchId())).withSelfRel(),
                linkTo(methodOn(CommonMatchController.class).getMatches()).withRel("matches"),
                linkTo(methodOn(CommonTeamController.class).getTeam(match.getHomeTeam().getTeamId())).withRel("homeTeam"),
                linkTo(methodOn(CommonTeamController.class).getTeam(match.getAwayTeam().getTeamId())).withRel("awayTeam"),
                linkTo(methodOn(CommonSeasonController.class).getSeason(match.getSeason().getSeasonId())).withRel("season"),
                linkTo(methodOn(CommonLocationController.class).getLocation(match.getLocation().getLocationId())).withRel("location")
        );
    }
}
