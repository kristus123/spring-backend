package com.example.demo.controllers.userControllers;

import com.example.demo.assembler.TeamResourceAssembler;
import com.example.demo.exceptions.TeamNotFoundException;
import com.example.demo.models.TeamModel;
import com.example.demo.services.TeamService;
import org.springframework.beans.factory.annotation.Autowired;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.*;

import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/v1/user")
public class UserTeamController {

    @Autowired
    private TeamService teamService;
    @Autowired
    private TeamResourceAssembler teamResourceAssembler;


    @GetMapping("/get/team/{id}")
    public Resource<TeamModel> oneTeam(@PathVariable Integer id) {

        TeamModel team = teamService.findById(id)
                .orElseThrow(() -> new TeamNotFoundException(id));

        return teamResourceAssembler.toResource(team);
    }

    @GetMapping("/get/team")
    public Resources<Resource<TeamModel>> allTeams() {

        List<Resource<TeamModel>> teams = teamService.findAll()
                .stream()
                .map(teamResourceAssembler::toResource)
                .collect(Collectors.toList());

        return new Resources<>(teams,
                linkTo(methodOn(UserTeamController.class).allTeams()).withSelfRel());
    }
}
