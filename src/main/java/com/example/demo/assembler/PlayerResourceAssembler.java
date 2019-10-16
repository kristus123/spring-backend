package com.example.demo.assembler;

import com.example.demo.controllers.commonControllers.CommonPlayerController;
import com.example.demo.models.PlayerModel;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.*;

import org.springframework.hateoas.Resource;
import org.springframework.hateoas.ResourceAssembler;
import org.springframework.stereotype.Component;

@Component
public class PlayerResourceAssembler implements ResourceAssembler<PlayerModel, Resource<PlayerModel>> {

    @Override
    public Resource<PlayerModel> toResource(PlayerModel playerModel) {
        return new Resource<>(playerModel,
                linkTo(methodOn(CommonPlayerController.class).getPlayer(playerModel.getPlayerId())).withSelfRel(),
                linkTo(methodOn(CommonPlayerController.class).getPlayers()).withRel("players"));
    }
}
