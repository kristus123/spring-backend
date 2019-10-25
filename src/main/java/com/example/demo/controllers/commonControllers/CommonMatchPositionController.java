package com.example.demo.controllers.commonControllers;

import com.example.demo.assembler.MatchPositionResourceAssembler;
import com.example.demo.dtos.MatchPositionDTO;
import com.example.demo.exceptions.ElementNotFoundException;
import com.example.demo.models.MatchPositionId;
import com.example.demo.models.MatchPositionModel;
import com.example.demo.services.MatchPositionService;
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
public class CommonMatchPositionController {

    @Autowired
    MatchPositionService matchPositionService;

    @Autowired
    MatchPositionResourceAssembler assembler;

    @GetMapping("/get/matchposition/{matchId}/{playerId}")
    public ResponseEntity<Resource<MatchPositionModel>> getMatchPosition(@PathVariable Integer matchId, @PathVariable Integer playerId) {

        MatchPositionModel matchPosition = matchPositionService.findById(new MatchPositionId(playerId, matchId))
                .orElseThrow(() -> new ElementNotFoundException("Could not find match position with matchID=" + matchId + " and playerID=" + playerId));

        Resource<MatchPositionModel> resource = assembler.toResource(matchPosition);

        return ResponseEntity
                .ok(resource);
    }

    @GetMapping("/get/matchposition")
    public ResponseEntity<Resources<Resource<MatchPositionModel>>> getMatchPositions() {

        List<Resource<MatchPositionModel>> matchPositions = matchPositionService.findAll()
                .stream()
                .map(assembler::toResource)
                .collect(Collectors.toList());

        // TODO PANDA: throw exception or return ResponseEntity.ok()?
        if (matchPositions.isEmpty())
            throw new ElementNotFoundException("No match positions registered");

        return ResponseEntity
                .ok(new Resources<>(matchPositions,
                        linkTo(methodOn(CommonMatchPositionController.class).getMatchPositions()).withSelfRel()));
    }
}