package com.example.demo.controllers;

import com.example.demo.assembler.MatchResourceAssembler;
import com.example.demo.exceptions.MatchNotFoundException;
import com.example.demo.models.MatchModel;
import com.example.demo.services.MatchService;
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
public class MatchController {

    @Autowired
    private MatchService matchService;
    @Autowired
    private MatchResourceAssembler matchResourceAssembler;

    @PostMapping("/create/match")
    public Resource<MatchModel> newMatch(@RequestBody MatchModel matchModel) throws URISyntaxException {

        Resource<MatchModel> resource = matchResourceAssembler.toResource(matchService.save(matchModel));
        /*
        ResponseEntity
                .created(new URI(resource.getId().expand().getHref()))
                .body(resource);
         */

        return resource;
    }

    @GetMapping("/browse/match/{id}")
    public Resource<MatchModel> oneMatch(@PathVariable Integer id) {

        MatchModel match = matchService.findById(id)
                .orElseThrow(() -> new MatchNotFoundException(id));

        return matchResourceAssembler.toResource(match);
    }

    @GetMapping("/browse/matches")
    public Resources<Resource<MatchModel>> allMatches() {


        List<Resource<MatchModel>> matches = matchService.findAll()
                .stream()
                .map(matchResourceAssembler::toResource)
                .collect(Collectors.toList());

        return new Resources<>(matches,
                linkTo(methodOn(MatchController.class).allMatches()).withSelfRel());
    }

}
