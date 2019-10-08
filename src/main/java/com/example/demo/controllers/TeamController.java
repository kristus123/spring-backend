package com.example.demo.controllers;

import com.example.demo.assembler.TeamResourceAssembler;
import com.example.demo.exceptions.TeamNotFoundException;
import com.example.demo.models.TeamModel;
import com.example.demo.services.TeamService;
import org.springframework.beans.factory.annotation.Autowired;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.*;

import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
import org.springframework.web.bind.annotation.*;

import java.net.URISyntaxException;
import java.util.List;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/api/v1")
public class TeamController {

    @Autowired
    private TeamService teamService;
    @Autowired
    private TeamResourceAssembler teamResourceAssembler;

    @PostMapping("/create/team")
    public Resource<TeamModel> newTeam(@RequestBody TeamModel teamModel) throws URISyntaxException {

        Resource<TeamModel> resource = teamResourceAssembler.toResource(teamService.save(teamModel));
        /*
        ResponseEntity
                .created(new URI(resource.getId().expand().getHref()))
                .body(resource);
         */

        return resource;
    }

    @GetMapping("/browse/team/{id}")
    public Resource<TeamModel> oneTeam(@PathVariable Integer id) {

        TeamModel team = teamService.findById(id)
                .orElseThrow(() -> new TeamNotFoundException(id));

        return teamResourceAssembler.toResource(team);
    }

    @GetMapping("/browse/teams")
    public Resources<Resource<TeamModel>> allTeams() {

        List<Resource<TeamModel>> teams = teamService.findAll()
                .stream()
                .map(teamResourceAssembler::toResource)
                .collect(Collectors.toList());

        return new Resources<>(teams,
                linkTo(methodOn(TeamController.class).allTeams()).withSelfRel());
    }
}
