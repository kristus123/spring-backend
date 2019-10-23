package com.example.demo.assembler;

import com.example.demo.controllers.commonControllers.CommonMatchController;
import com.example.demo.controllers.commonControllers.CommonMatchGoalController;
import com.example.demo.controllers.commonControllers.CommonPlayerController;
import com.example.demo.models.MatchGoalModel;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.ResourceAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.*;

@Component
public class MatchGoalResourceAssembler implements ResourceAssembler<MatchGoalModel, Resource<MatchGoalModel>> {

    @Override
    public Resource<MatchGoalModel> toResource(MatchGoalModel matchGoal) {
        return new Resource<>(matchGoal,
                linkTo(methodOn(CommonMatchGoalController.class).getMatchGoal(matchGoal.getGoalId())).withSelfRel(),
                linkTo(methodOn(CommonMatchGoalController.class).getMatchGoals()).withRel("matchGoals"),
                linkTo(methodOn(CommonPlayerController.class).getPlayer(matchGoal.getPlayer().getPlayerId())).withRel("player"),
                linkTo(methodOn(CommonMatchController.class).getMatch(matchGoal.getMatch().getMatchId())).withRel("match")
                // TODO PANDA: how to include goal type?
        );
    }
}