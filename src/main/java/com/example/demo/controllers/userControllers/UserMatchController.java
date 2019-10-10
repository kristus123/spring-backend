package com.example.demo.controllers.userControllers;

import com.example.demo.assembler.MatchResourceAssembler;
import com.example.demo.exceptions.MatchNotFoundException;
import com.example.demo.models.MatchModel;
import com.example.demo.services.MatchService;
import org.springframework.beans.factory.annotation.Autowired;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.*;

import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/v1/user")
public class UserMatchController {

    @Autowired
    private MatchService matchService;
    @Autowired
    private MatchResourceAssembler matchResourceAssembler;


    @GetMapping("/get/match/{id}")
    public Resource<MatchModel> oneMatch(@PathVariable Integer id) {

        MatchModel match = matchService.findById(id)
                .orElseThrow(() -> new MatchNotFoundException(id));

        return matchResourceAssembler.toResource(match);
    }

    @GetMapping("/get/match")
    public Resources<Resource<MatchModel>> allMatches() {

        List<Resource<MatchModel>> matches = matchService.findAll()
                .stream()
                .map(matchResourceAssembler::toResource)
                .collect(Collectors.toList());

        return new Resources<>(matches,
                linkTo(methodOn(UserMatchController.class).allMatches()).withSelfRel());
    }

}
