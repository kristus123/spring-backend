package com.example.demo.controllers.userControllers;

import com.example.demo.assembler.SeasonResourceAssembler;
import com.example.demo.exceptions.SeasonNotFoundException;
import com.example.demo.models.SeasonModel;
import com.example.demo.services.SeasonService;
import org.springframework.beans.factory.annotation.Autowired;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.*;

import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/v1/user")
public class UserSeasonController {

    @Autowired
    private SeasonService seasonService;
    @Autowired
    private SeasonResourceAssembler seasonResourceAssembler;


    @GetMapping("/get/season/{id}")
    public Resource<SeasonModel> oneSeason(@PathVariable Integer id) {

        SeasonModel season = seasonService.findById(id)
                .orElseThrow(() -> new SeasonNotFoundException(id));

        return seasonResourceAssembler.toResource(season);
    }

    @GetMapping("/get/season")
    public Resources<Resource<SeasonModel>> allSeasons() {

        List<Resource<SeasonModel>> seasons = seasonService.findAll()
                .stream()
                .map(seasonResourceAssembler::toResource)
                .collect(Collectors.toList());

        return new Resources<>(seasons,
                linkTo(methodOn(UserSeasonController.class).allSeasons()).withSelfRel());
    }

}
