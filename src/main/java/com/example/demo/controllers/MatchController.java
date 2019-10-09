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
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/v1")
public class MatchController {

    @Autowired
    private MatchService matchService;
    @Autowired
    private MatchResourceAssembler matchResourceAssembler;

    @PostMapping("/admin/post/match")
    public Resource<MatchModel> newMatch(@RequestBody MatchModel matchModel) throws URISyntaxException {

        Resource<MatchModel> resource = matchResourceAssembler.toResource(matchService.save(matchModel));
        /*
        ResponseEntity
                .created(new URI(resource.getId().expand().getHref()))
                .body(resource);
         */

        return resource;
    }

    @PutMapping("/admin/update/match/{id}")
    public Resource<MatchModel> updateMatch(@PathVariable Integer id, @RequestBody MatchModel matchModel) {
        if (matchModel.getMatchId() != id || !matchService.findById(id).isPresent()) {
            //ResponseEntity.badRequest().build();
            return null;
        }

        //return ResponseEntity.ok(associationService.save(associationModel));
        return matchResourceAssembler.toResource(matchService.save(matchModel));
    }

    @DeleteMapping("/admin/delete/match/{id}")
    public Resource<MatchModel> deleteMatch(@PathVariable Integer id) {
        Optional<MatchModel> match = matchService.findById(id);
        if (!match.isPresent()) {
            //ResponseEntity.badRequest().build();
            return null;
        }

        matchService.deleteById(id);

        //return ResponseEntity.ok().build();
        return matchResourceAssembler.toResource(match.get());
    }

    @GetMapping("/user/get/match/{id}")
    public Resource<MatchModel> oneMatch(@PathVariable Integer id) {

        MatchModel match = matchService.findById(id)
                .orElseThrow(() -> new MatchNotFoundException(id));

        return matchResourceAssembler.toResource(match);
    }

    @GetMapping("/user/get/matches")
    public Resources<Resource<MatchModel>> allMatches() {

        List<Resource<MatchModel>> matches = matchService.findAll()
                .stream()
                .map(matchResourceAssembler::toResource)
                .collect(Collectors.toList());

        return new Resources<>(matches,
                linkTo(methodOn(MatchController.class).allMatches()).withSelfRel());
    }

}
