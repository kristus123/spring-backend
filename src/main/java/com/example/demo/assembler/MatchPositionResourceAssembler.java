package com.example.demo.assembler;

import com.example.demo.controllers.commonControllers.CommonMatchPositionController;
import com.example.demo.dtos.MatchPositionDTO;
import com.example.demo.models.MatchPositionModel;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.ResourceAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.*;


@Component
public class MatchPositionResourceAssembler implements ResourceAssembler<MatchPositionModel, Resource<MatchPositionModel>> {

    @Override
    public Resource<MatchPositionModel> toResource(MatchPositionModel matchPosition) {
        return new Resource<>(matchPosition,
                linkTo(methodOn(CommonMatchPositionController.class).getMatchPosition(matchPosition.getMatch().getMatchId(), matchPosition.getPlayer().getPlayerId())).withSelfRel(),
                linkTo(methodOn(CommonMatchPositionController.class).getMatchPositions()).withRel("matchPositions")
                // TODO PANDA: how to include position type?
        );
    }
}