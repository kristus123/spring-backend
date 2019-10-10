package com.example.demo.controllers.userControllers;

import com.example.demo.assembler.PlayerResourceAssembler;
import com.example.demo.exceptions.PlayerNotFoundException;
import com.example.demo.models.PlayerModel;
import com.example.demo.services.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.*;

import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/v1/user")
public class UserPlayerController {

    @Autowired
    private PlayerService playerService;
    @Autowired
    private PlayerResourceAssembler playerResourceAssembler;



    @GetMapping("/get/player/{id}")
    public Resource<PlayerModel> onePlayer(@PathVariable Integer id) {

        PlayerModel player = playerService.findById(id)
                .orElseThrow(() -> new PlayerNotFoundException(id));

        return playerResourceAssembler.toResource(player);
    }

    @GetMapping("/get/player")
    public Resources<Resource<PlayerModel>> allPlayers() {

        List<Resource<PlayerModel>> players = playerService.findAll()
                .stream()
                .map(playerResourceAssembler::toResource)
                .collect(Collectors.toList());

        return new Resources<>(players,
                linkTo(methodOn(UserPlayerController.class).allPlayers()).withSelfRel());
    }



}
