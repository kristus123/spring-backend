package com.example.demo.assembler;

import com.example.demo.controllers.userControllers.UserWatchPlayerController;
import com.example.demo.models.PlayerModel;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.ResourceAssembler;
import org.springframework.stereotype.Component;

import java.security.Principal;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.*;

@Component
public class UserPlayerResourceAssembler implements ResourceAssembler<PlayerModel, Resource<PlayerModel>> {

    Principal principal;

    public void setPrincipal(Principal principal) {
        this.principal = principal;
    }

    @Override
    public Resource<PlayerModel> toResource(PlayerModel player) {
        return new Resource<>(player,
                linkTo(methodOn(UserWatchPlayerController.class).getPlayer(player.getPlayerId(), principal)).withSelfRel(),
                linkTo(methodOn(UserWatchPlayerController.class).getPlayers(principal)).withRel("teams"));
    }
}
