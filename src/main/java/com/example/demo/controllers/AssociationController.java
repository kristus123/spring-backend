package com.example.demo.controllers;

import com.example.demo.assembler.AssociationResourceAssembler;
import com.example.demo.exceptions.AssociationNotFoundException;
import com.example.demo.models.AssociationModel;
import com.example.demo.services.AssociationService;
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
public class AssociationController {

    @Autowired
    private AssociationService associationService;
    @Autowired
    private AssociationResourceAssembler associationResourceAssembler;


    @PostMapping("/create/association")
    public Resource<AssociationModel> newPlayer(@RequestBody AssociationModel associationModel) throws URISyntaxException {

        Resource<AssociationModel> resource = associationResourceAssembler.toResource(associationService.save(associationModel));
        /*
        ResponseEntity
                .created(new URI(resource.getId().expand().getHref()))
                .body(resource);
         */

        return resource;
    }

    @GetMapping("/browse/association/{id}")
    public Resource<AssociationModel> onePlayer(@PathVariable Integer id) {

        AssociationModel association = associationService.findById(id)
                .orElseThrow(() -> new AssociationNotFoundException(id));

        return associationResourceAssembler.toResource(association);
    }

    @GetMapping("/browse/associations")
    public Resources<Resource<AssociationModel>> allPlayers() {

        List<Resource<AssociationModel>> associations = associationService.findAll()
                .stream()
                .map(associationResourceAssembler::toResource)
                .collect(Collectors.toList());

        return new Resources<>(associations,
                linkTo(methodOn(PlayerController.class).allPlayers()).withSelfRel());
    }
}
