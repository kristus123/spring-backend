package com.example.demo.assembler;

import com.example.demo.controllers.PlayerController;
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
                linkTo(methodOn(PlayerController.class).onePlayer(playerModel.getPlayer_id())).withSelfRel(),
                linkTo(methodOn(PlayerController.class).allPlayers()).withRel("players"));
    }
}
