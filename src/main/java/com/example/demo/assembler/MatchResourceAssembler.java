package com.example.demo.assembler;

import com.example.demo.controllers.userControllers.UserMatchController;
import com.example.demo.models.MatchModel;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.*;

import org.springframework.hateoas.Resource;
import org.springframework.hateoas.ResourceAssembler;
import org.springframework.stereotype.Component;

@Component
public class MatchResourceAssembler implements ResourceAssembler<MatchModel, Resource<MatchModel>> {
    @Override
    public Resource<MatchModel> toResource(MatchModel matchModel) {
        return new Resource<>(matchModel,
                linkTo(methodOn(UserMatchController.class).getMatch(matchModel.getMatchId())).withSelfRel(),
                linkTo(methodOn(UserMatchController.class).getMatches()).withRel("matches"));
    }
}
