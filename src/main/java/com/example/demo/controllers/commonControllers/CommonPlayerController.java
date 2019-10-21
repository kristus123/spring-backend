package com.example.demo.controllers.commonControllers;

import com.example.demo.assembler.PlayerResourceAssembler;
import com.example.demo.exceptions.ElementNotFoundException;
import com.example.demo.models.PlayerModel;
import com.example.demo.services.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@RestController
@RequestMapping("/v1/common")
public class CommonPlayerController {

    @Autowired
    PlayerService playerService;

    @Autowired
    PlayerResourceAssembler assembler;


    @GetMapping("/get/player/{id}")
    public ResponseEntity<Resource<PlayerModel>> getPlayer(@PathVariable Integer id) {

        PlayerModel player = playerService.findById(id)
                .orElseThrow(() -> new ElementNotFoundException("Could not find player with ID=" + id));

        Resource<PlayerModel> resource = assembler.toResource(player);

        return ResponseEntity
                .ok(resource);
    }

    @GetMapping("/get/player")
    public ResponseEntity<Resources<Resource<PlayerModel>>> getPlayers() {

        List<Resource<PlayerModel>> players = playerService.findAll()
                .stream()
                .map(assembler::toResource)
                .collect(Collectors.toList());

        // TODO PANDA: throw exception or return ResponseEntity.ok()?
        if (players.isEmpty())
            throw new ElementNotFoundException("No players registered");

        return ResponseEntity
                .ok(new Resources<>(players,
                        linkTo(methodOn(CommonPlayerController.class).getPlayers()).withSelfRel()));
    }



}
