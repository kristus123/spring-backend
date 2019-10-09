package com.example.demo.controllers;

import com.example.demo.assembler.PlayerResourceAssembler;
import com.example.demo.exceptions.PlayerNotFoundException;
import com.example.demo.models.PlayerModel;
import com.example.demo.services.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.*;

import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/v1")
public class PlayerController {

    @Autowired
    private PlayerService playerService;
    @Autowired
    private PlayerResourceAssembler playerResourceAssembler;


    @PostMapping("/admin/post/player")
    public Resource<PlayerModel> newPlayer(@RequestBody PlayerModel playerModel) throws URISyntaxException {

        Resource<PlayerModel> resource = playerResourceAssembler.toResource(playerService.save(playerModel));
        /*
        ResponseEntity
                .created(new URI(resource.getId().expand().getHref()))
                .body(resource);
         */

        return resource;
    }

    @GetMapping("/user/get/player/{id}")
    public Resource<PlayerModel> onePlayer(@PathVariable Integer id) {

        PlayerModel player = playerService.findById(id)
                .orElseThrow(() -> new PlayerNotFoundException(id));

        return playerResourceAssembler.toResource(player);
    }

    @GetMapping("/user/get/players")
    public Resources<Resource<PlayerModel>> allPlayers() {

        List<Resource<PlayerModel>> players = playerService.findAll()
                .stream()
                .map(playerResourceAssembler::toResource)
                .collect(Collectors.toList());

        return new Resources<>(players,
                linkTo(methodOn(PlayerController.class).allPlayers()).withSelfRel());
    }



}
