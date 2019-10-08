package com.example.demo.controllers;

import com.example.demo.assembler.PlayerResourceAssembler;
import com.example.demo.assembler.TeamResourceAssembler;
import com.example.demo.models.PlayerModel;
import com.example.demo.models.TeamModel;
import com.example.demo.repositories.PlayerRepository;
import com.example.demo.repositories.TeamRepository;
import org.springframework.beans.factory.annotation.Autowired;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.*;

import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/browse")
public class StatsController {

    @Autowired
    private PlayerRepository playerRepository;
    @Autowired
    private TeamRepository teamRepository;

    @Autowired
    private PlayerResourceAssembler playerResourceAssembler;
    @Autowired
    private TeamResourceAssembler teamResourceAssembler;

    @GetMapping("/players")
    public Resources<Resource<PlayerModel>> allPlayers() {

        List<Resource<PlayerModel>> players = playerRepository.findAll()
                .stream()
                .map(playerResourceAssembler::toResource)
                .collect(Collectors.toList());

        return new Resources<>(players,
                linkTo(methodOn(StatsController.class).allPlayers()).withSelfRel());
    }

    @GetMapping("/teams")
    public Resources<Resource<TeamModel>> allTeams() {

        List<Resource<TeamModel>> teams = teamRepository.findAll()
                .stream()
                .map(teamResourceAssembler::toResource)
                .collect(Collectors.toList());

        return new Resources<>(teams,
                linkTo(methodOn(StatsController.class).allTeams()).withSelfRel());
    }


}
