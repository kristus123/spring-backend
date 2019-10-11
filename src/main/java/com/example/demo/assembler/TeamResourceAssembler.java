package com.example.demo.assembler;

import com.example.demo.controllers.userControllers.UserTeamController;
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
                linkTo(methodOn(UserTeamController.class).getTeam(teamModel.getTeamId())).withSelfRel(),
                linkTo(methodOn(UserTeamController.class).getTeams()).withRel("teams"));
    }
}
