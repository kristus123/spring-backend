package com.example.demo.controllers;

import com.example.demo.assembler.SeasonResourceAssembler;
import com.example.demo.exceptions.SeasonNotFoundException;
import com.example.demo.models.SeasonModel;
import com.example.demo.services.SeasonService;
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
public class SeasonController {

    @Autowired
    private SeasonService seasonService;
    @Autowired
    private SeasonResourceAssembler seasonResourceAssembler;

    @PostMapping("/create/season")
    public Resource<SeasonModel> newSeason(@RequestBody SeasonModel seasonModel) throws URISyntaxException {

        Resource<SeasonModel> resource = seasonResourceAssembler.toResource(seasonService.save(seasonModel));
        /*
        ResponseEntity
                .created(new URI(resource.getId().expand().getHref()))
                .body(resource);
         */

        return resource;
    }

    @GetMapping("/browse/season/{id}")
    public Resource<SeasonModel> oneSeason(@PathVariable Integer id) {

        SeasonModel season = seasonService.findById(id)
                .orElseThrow(() -> new SeasonNotFoundException(id));

        return seasonResourceAssembler.toResource(season);
    }

    @GetMapping("/browse/seasons")
    public Resources<Resource<SeasonModel>> allSeasons() {

        List<Resource<SeasonModel>> seasons = seasonService.findAll()
                .stream()
                .map(seasonResourceAssembler::toResource)
                .collect(Collectors.toList());

        return new Resources<>(seasons,
                linkTo(methodOn(SeasonController.class).allSeasons()).withSelfRel());
    }

}
