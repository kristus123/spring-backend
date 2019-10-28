package com.example.demo.controllers.commonControllers;

import com.example.demo.assembler.MatchResourceAssembler;
import com.example.demo.dtos.MatchDTO;
import com.example.demo.dtos.MatchGoalDTO;
import com.example.demo.dtos.MatchResultDTO;
import com.example.demo.exceptions.ElementNotFoundException;
import com.example.demo.models.MatchModel;
import com.example.demo.services.MatchService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@RestController
@RequestMapping("/v1/common")
public class CommonMatchController {

    @Autowired
    MatchService matchService;

    @Autowired
    MatchResourceAssembler assembler;


    @GetMapping("/get/match/{id}")
    public ResponseEntity<Resource<MatchModel>> getMatch(@PathVariable Integer id) {

        MatchModel match = matchService.findById(id)
                .orElseThrow(() -> new ElementNotFoundException("Could not find match with ID=" + id));

        Resource<MatchModel> resource = assembler.toResource(match);

        return ResponseEntity
                .ok(resource);
    }

    @GetMapping("/get/match/{teamId}/byTeam")
    public List<HashMap<String, Object>> getMatchesByTeamId (@PathVariable int teamId) {

        List<MatchModel> matches = matchService.findByTeam(teamId);
        List<HashMap<String, Object>> l = new ArrayList<>();
        matches.forEach(match -> {
            l.add( matchService.getFilteredMatchStats(match, teamId));
        });
        return l;
    }

    @GetMapping("/get/match")
    public ResponseEntity<Resources<Resource<MatchModel>>> getMatches() {

        List<Resource<MatchModel>> matches = matchService.findAll()
                .stream()
                .map(assembler::toResource)
                .collect(Collectors.toList());

        // TODO PANDA: throw exception or return ResponseEntity.ok()?
        if (matches.isEmpty())
            throw new ElementNotFoundException("No matches registered");

        return ResponseEntity
                .ok(new Resources<>(matches,
                        linkTo(methodOn(CommonMatchController.class).getMatches()).withSelfRel()));
    }

}
