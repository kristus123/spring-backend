package com.example.demo.controllers.commonControllers;

import com.example.demo.assembler.CoachResourceAssembler;
import com.example.demo.exceptions.ElementNotFoundException;
import com.example.demo.models.CoachModel;
import com.example.demo.services.CoachService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@RestController
@RequestMapping("/v1/common")
public class CommonCoachController {

    @Autowired
    CoachService coachService;

    @Autowired
    CoachResourceAssembler assembler;

    @GetMapping("/get/coach/{id}")
    public ResponseEntity<Resource<CoachModel>> getCoach(@PathVariable Integer id) {

        CoachModel coach = coachService.findById(id)
                .orElseThrow(() -> new ElementNotFoundException("Could not find coach with ID=" + id));

        Resource<CoachModel> resource = assembler.toResource(coach);

        return ResponseEntity
                .ok(resource);
    }

    @GetMapping("/get/coach")
    public ResponseEntity<Resources<Resource<CoachModel>>> getCoaches() {

        List<Resource<CoachModel>> coaches = coachService.findAllActive()
                .stream()
                .map(assembler::toResource)
                .collect(Collectors.toList());

        // TODO PANDA: throw exception or return ResponseEntity.ok()?
        if (coaches.isEmpty())
            throw new ElementNotFoundException("No coaches registered");

        return ResponseEntity
                .ok(new Resources<>(coaches,
                        linkTo(methodOn(CommonCoachController.class).getCoaches()).withSelfRel()));
    }
}
