package com.example.demo.assembler;

import com.example.demo.controllers.commonControllers.CommonPersonController;
import com.example.demo.controllers.commonControllers.CommonPlayerController;
import com.example.demo.controllers.commonControllers.CommonTeamController;
import com.example.demo.models.PlayerModel;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.*;

import org.springframework.hateoas.Resource;
import org.springframework.hateoas.ResourceAssembler;
import org.springframework.stereotype.Component;

@Component
public class PlayerResourceAssembler implements ResourceAssembler<PlayerModel, Resource<PlayerModel>> {

    @Override
    public Resource<PlayerModel> toResource(PlayerModel player) {
        return new Resource<>(player,
                linkTo(methodOn(CommonPlayerController.class).getPlayer(player.getPlayerId())).withSelfRel(),
                linkTo(methodOn(CommonPlayerController.class).getPlayers()).withRel("players"),
                linkTo(methodOn(CommonPersonController.class).getPerson(player.getPerson().getPersonId())).withRel("person"),
                linkTo(methodOn(CommonTeamController.class).getTeam(player.getTeam().getTeamId())).withRel("team")
        );
    }
}
