package com.example.demo.controllers.commonControllers;

import com.example.demo.assembler.TeamResourceAssembler;
import com.example.demo.exceptions.ElementNotFoundException;
import com.example.demo.models.TeamModel;
import com.example.demo.services.TeamService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
import org.springframework.web.bind.annotation.*;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.*;

import java.util.List;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/v1/common")
public class CommonTeamController {

    @Autowired
    TeamService teamService;

    @Autowired
    TeamResourceAssembler assembler;


    @GetMapping("/get/team/{id}")
    public Resource<TeamModel> getTeam(@PathVariable Integer id) {

        TeamModel team = teamService.findById(id)
                .orElseThrow(() -> new ElementNotFoundException("Could not find team with ID=" + id));

        return assembler.toResource(team);
    }

    @GetMapping("/get/team")
    public Resources<Resource<TeamModel>> getTeams() {

        List<Resource<TeamModel>> teams = teamService.findAllActive()
                .stream()
                .map(assembler::toResource)
                .collect(Collectors.toList());

        if (teams.isEmpty())
            throw new ElementNotFoundException("Could not find any data about teams");

        return new Resources<>(teams,
                linkTo(methodOn(CommonTeamController.class).getTeams()).withSelfRel());
    }
}
