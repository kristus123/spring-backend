package com.example.demo.assembler;

import com.example.demo.controllers.commonControllers.*;
import com.example.demo.models.TeamModel;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.*;

import org.springframework.hateoas.Resource;
import org.springframework.hateoas.ResourceAssembler;
import org.springframework.stereotype.Component;

@Component
public class TeamResourceAssembler implements ResourceAssembler<TeamModel, Resource<TeamModel>> {

    @Override
    public Resource<TeamModel> toResource(TeamModel team) {
        return new Resource<>(team,
                linkTo(methodOn(CommonTeamController.class).getTeam(team.getTeamId())).withSelfRel(),
                linkTo(methodOn(CommonTeamController.class).getTeams()).withRel("teams"),
                linkTo(methodOn(CommonAssociationController.class).getAssociation(team.getAssociation().getAssociationId())).withRel("association"),
                linkTo(methodOn(CommonCoachController.class).getCoach(team.getCoach().getCoachId())).withRel("coach"),
                linkTo(methodOn(CommonOwnerController.class).getOwner(team.getOwner().getOwnerId())).withRel("owner"),
                linkTo(methodOn(CommonLocationController.class).getLocation(team.getLocation().getLocationId())).withRel("location")
        );
    }
}
