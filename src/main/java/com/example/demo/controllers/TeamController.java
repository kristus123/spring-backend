package com.example.demo.controllers;

import com.example.demo.assembler.TeamResourceAssembler;
import com.example.demo.exceptions.InvalidTeamRequestException;
import com.example.demo.exceptions.TeamNotFoundException;
import com.example.demo.models.AssociationModel;
import com.example.demo.models.TeamModel;
import com.example.demo.services.TeamService;
import org.springframework.beans.factory.annotation.Autowired;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.*;

import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
import org.springframework.web.bind.annotation.*;

import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/v1")
public class TeamController {

    @Autowired
    private TeamService teamService;
    @Autowired
    private TeamResourceAssembler teamResourceAssembler;

    @PostMapping("/admin/post/team")
    public Resource<TeamModel> newTeam(@RequestBody TeamModel teamModel) throws URISyntaxException {
        Resource<TeamModel> resource = teamResourceAssembler.toResource(teamService.save(teamModel));
        /*
        ResponseEntity
                .created(new URI(resource.getId().expand().getHref()))
                .body(resource);
         */
        return resource;
    }

    @PutMapping("/admin/update/team/{id}")
    public Resource<TeamModel> updateTeam(@PathVariable Integer id, @RequestBody TeamModel teamModel) {
        if (id != teamModel.getTeamId()) {
            throw new InvalidTeamRequestException(id, teamModel.getTeamId());
        }
        if (!teamService.findById(id).isPresent()) {
            //ResponseEntity.badRequest().build();
            throw new TeamNotFoundException(id);
        }

        //return ResponseEntity.ok(associationService.save(associationModel));
        return teamResourceAssembler.toResource(teamService.save(teamModel));
    }

    @DeleteMapping("/admin/delete/team/{id}")
    public Resource<TeamModel> deleteTeam(@PathVariable Integer id) {
        Optional<TeamModel> team = teamService.findById(id);
        if (!team.isPresent()) {
            //ResponseEntity.badRequest().build();
            throw new TeamNotFoundException(id);
        }

        teamService.deleteById(id);

        //return ResponseEntity.ok().build();
        return teamResourceAssembler.toResource(team.get());
    }

    @GetMapping("/user/get/team/{id}")
    public Resource<TeamModel> oneTeam(@PathVariable Integer id) {

        TeamModel team = teamService.findById(id)
                .orElseThrow(() -> new TeamNotFoundException(id));

        return teamResourceAssembler.toResource(team);
    }

    @GetMapping("/user/get/teams")
    public Resources<Resource<TeamModel>> allTeams() {

        List<Resource<TeamModel>> teams = teamService.findAll()
                .stream()
                .map(teamResourceAssembler::toResource)
                .collect(Collectors.toList());

        return new Resources<>(teams,
                linkTo(methodOn(TeamController.class).allTeams()).withSelfRel());
    }
}
