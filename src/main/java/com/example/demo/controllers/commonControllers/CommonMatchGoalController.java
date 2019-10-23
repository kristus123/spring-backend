package com.example.demo.controllers.commonControllers;

import com.example.demo.assembler.MatchGoalResourceAssembler;
import com.example.demo.exceptions.ElementNotFoundException;
import com.example.demo.models.ContactModel;
import com.example.demo.models.MatchGoalModel;
import com.example.demo.services.MatchGoalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;


@RestController
@RequestMapping("/v1/common")
public class CommonMatchGoalController {

    @Autowired
    MatchGoalService matchGoalService;

    @Autowired
    MatchGoalResourceAssembler assembler;

    @GetMapping("/get/matchgoal/{id}")
    public ResponseEntity<Resource<MatchGoalModel>> getMatchGoal(@PathVariable Integer id) {

        MatchGoalModel matchGoal = matchGoalService.findById(id)
                .orElseThrow(() -> new ElementNotFoundException("Could not find match goal with ID=" + id));

        Resource<MatchGoalModel> resource = assembler.toResource(matchGoal);

        return ResponseEntity
                .ok(resource);
    }

    @GetMapping("/get/matchgoal")
    public ResponseEntity<Resources<Resource<MatchGoalModel>>> getMatchGoals() {

        List<Resource<MatchGoalModel>> matchGoals = matchGoalService.findAll()
                .stream()
                .map(assembler::toResource)
                .collect(Collectors.toList());

        // TODO PANDA: throw exception or return ResponseEntity.ok()?
        if (matchGoals.isEmpty())
            throw new ElementNotFoundException("No match goals registered");

        return ResponseEntity
                .ok(new Resources<>(matchGoals,
                        linkTo(methodOn(CommonMatchGoalController.class).getMatchGoals()).withSelfRel()));
    }

    @GetMapping("/get/matchgoal/{matchId}/byMatchId")
    public List<MatchGoalModel> getMatchGoalsGivenByMatchId(@PathVariable Integer matchId) {
        return matchGoalService.findByMatchId(matchId);
    }

    @GetMapping("/get/matchgoal/{playerId}/byPlayerId")
    public List<MatchGoalModel> getMatchGoalsGivenByPlayerId(@PathVariable Integer playerId) {
        return matchGoalService.findByPlayerId(playerId);
    }
}

