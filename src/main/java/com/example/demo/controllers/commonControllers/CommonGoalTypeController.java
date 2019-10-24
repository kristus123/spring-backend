package com.example.demo.controllers.commonControllers;

import com.example.demo.assembler.GoalTypeResourceAssembler;
import com.example.demo.exceptions.ElementNotFoundException;
import com.example.demo.models.GoalTypeModel;
import com.example.demo.services.GoalTypeService;
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
public class CommonGoalTypeController {

    @Autowired
    GoalTypeService goalTypeService;

    @Autowired
    GoalTypeResourceAssembler assembler;

    @GetMapping("/get/goaltype/{id}")
    public ResponseEntity<Resource<GoalTypeModel>> getGoalType(@PathVariable Integer id) {
        GoalTypeModel goalType = goalTypeService.findById(id)
                .orElseThrow(() -> new ElementNotFoundException("Could not find goal type with ID=" + id));

        Resource<GoalTypeModel> resource = assembler.toResource(goalType);

        return ResponseEntity
                .ok(resource);
    }

    @GetMapping("/get/goaltype")
    public ResponseEntity<Resources<Resource<GoalTypeModel>>> getGoalTypes() {

        List<Resource<GoalTypeModel>> goalTypes = goalTypeService.findAll()
                .stream()
                .map(assembler::toResource)
                .collect(Collectors.toList());

        // TODO PANDA: throw exception or return ResponseEntity.ok()?
        if (goalTypes.isEmpty())
            throw new ElementNotFoundException("No goal types registered");

        return ResponseEntity
                .ok(new Resources<>(goalTypes,
                        linkTo(methodOn(CommonGoalTypeController.class).getGoalTypes()).withSelfRel()));
    }

}
