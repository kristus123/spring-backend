package com.example.demo.controllers.commonControllers;

import com.example.demo.assembler.SeasonResourceAssembler;
import com.example.demo.exceptions.ElementNotFoundException;
import com.example.demo.models.SeasonModel;
import com.example.demo.services.SeasonService;
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
public class CommonSeasonController {

    @Autowired
    private SeasonService seasonService;

    @Autowired
    SeasonResourceAssembler assembler;

    @GetMapping("/get/season/{id}")
    public ResponseEntity<Resource<SeasonModel>> getSeason(@PathVariable Integer id) {

        SeasonModel season = seasonService.findById(id)
                .orElseThrow(() -> new ElementNotFoundException("Could not find season with ID=" + id));

        Resource<SeasonModel> resource = assembler.toResource(season);

        return ResponseEntity
                .ok(resource);
    }

    @GetMapping("/get/season")
    public ResponseEntity<Resources<Resource<SeasonModel>>> getSeasons() {

        List<Resource<SeasonModel>> seasons = seasonService.findAll()
                .stream()
                .map(assembler::toResource)
                .collect(Collectors.toList());

        // TODO PANDA: throw exception or return ResponseEntity.ok()?
        if (seasons.isEmpty())
            throw new ElementNotFoundException("No season registered");

        return ResponseEntity
                .ok(new Resources<>(seasons,
                        linkTo(methodOn(CommonSeasonController.class).getSeasons()).withSelfRel()));
    }

}
