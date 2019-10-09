package com.example.demo.assembler;

import com.example.demo.controllers.TeamController;
import com.example.demo.models.TeamModel;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.*;

import org.springframework.hateoas.Resource;
import org.springframework.hateoas.ResourceAssembler;
import org.springframework.stereotype.Component;

@Component
public class TeamResourceAssembler implements ResourceAssembler<TeamModel, Resource<TeamModel>> {

    @Override
    public Resource<TeamModel> toResource(TeamModel teamModel) {
        return new Resource<>(teamModel,
                linkTo(methodOn(TeamController.class).oneTeam(teamModel.getTeamId())).withSelfRel(),
                linkTo(methodOn(TeamController.class).allTeams()).withRel("teams"));
    }
}
