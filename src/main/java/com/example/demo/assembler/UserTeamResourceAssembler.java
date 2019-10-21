package com.example.demo.assembler;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.*;

import com.example.demo.controllers.userControllers.UserWatchTeamController;
import com.example.demo.models.TeamModel;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.ResourceAssembler;
import org.springframework.stereotype.Component;

import java.security.Principal;

@Component
public class UserTeamResourceAssembler implements ResourceAssembler<TeamModel, Resource<TeamModel>> {

    Principal principal;

    public void setPrincipal(Principal principal) {
        this.principal = principal;
    }

    @Override
    public Resource<TeamModel> toResource(TeamModel team) {
        return new Resource<>(team,
                linkTo(methodOn(UserWatchTeamController.class).getTeam(team.getTeamId(), principal)).withSelfRel(),
                linkTo(methodOn(UserWatchTeamController.class).getTeams(principal)).withRel("teams"));
    }
}
