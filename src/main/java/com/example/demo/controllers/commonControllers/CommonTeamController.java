package com.example.demo.controllers.commonControllers;

import com.example.demo.assembler.TeamResourceAssembler;
import com.example.demo.exceptions.ElementNotFoundException;
import com.example.demo.models.TeamModel;
import com.example.demo.services.TeamService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.*;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/v1/common")
public class CommonTeamController {

    @Autowired TeamService teamService;

    @Autowired TeamResourceAssembler assembler;

    @GetMapping("/get/team/{id}")
    public ResponseEntity<Resource<TeamModel>> getTeam(@PathVariable Integer id) {

        TeamModel team = teamService.findById(id)
                .orElseThrow(() -> new ElementNotFoundException("Could not find team with ID=" + id));

        Resource<TeamModel> resource = assembler.toResource(team);

        return ResponseEntity
                .ok(resource);
    }

    @GetMapping("/get/team/thatHasCoach/{coachId}")
    public List<TeamModel> getAllTeamThatHasCoach(@PathVariable int coachId ) {
        return teamService.findByCoach(coachId);
    }

    @GetMapping("/get/team")
    public List<TeamModel> getTeams() {
        return teamService.findAllActive();
    }

    @GetMapping("/get/team/with-no-coach")
    public List<TeamModel> getTeamsWithNoCoach() {
        return teamService.findAllActive().stream().filter(team -> team.getCoach() == null).collect(Collectors.toList());
    }
}
