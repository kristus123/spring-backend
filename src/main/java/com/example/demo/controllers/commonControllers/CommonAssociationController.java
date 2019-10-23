package com.example.demo.controllers.commonControllers;

import com.example.demo.assembler.AssociationResourceAssembler;
import com.example.demo.exceptions.ElementNotFoundException;
import com.example.demo.models.AssociationModel;
import com.example.demo.services.AssociationService;
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
public class CommonAssociationController {

    @Autowired
    AssociationService associationService;

    @Autowired
    AssociationResourceAssembler assembler;


    @GetMapping("/get/association/{id}")
    public ResponseEntity<Resource<AssociationModel>> getAssociation(@PathVariable Integer id) {

        AssociationModel association = associationService.findById(id)
                .orElseThrow(() -> new ElementNotFoundException("Could not find association with ID=" + id));

        Resource<AssociationModel> resource = assembler.toResource(association);

        return ResponseEntity
                .ok(resource);
    }

    @GetMapping("/get/association")
    public ResponseEntity<Resources<Resource<AssociationModel>>> getAssociations() {

        List<Resource<AssociationModel>> associations = associationService.findAll()
                .stream()
                .map(assembler::toResource)
                .collect(Collectors.toList());

        // TODO PANDA: throw exception or return ResponseEntity.ok()?
        if (associations.isEmpty())
            throw new ElementNotFoundException("No associations registered");

        return ResponseEntity
                .ok(new Resources<>(associations,
                        linkTo(methodOn(CommonAssociationController.class).getAssociations()).withSelfRel()));
    }
}
