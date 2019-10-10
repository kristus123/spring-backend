package com.example.demo.controllers.userControllers;

import com.example.demo.assembler.AssociationResourceAssembler;
import com.example.demo.exceptions.AssociationNotFoundException;
import com.example.demo.models.AssociationModel;
import com.example.demo.services.AssociationService;
import org.springframework.beans.factory.annotation.Autowired;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.*;

import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/v1/user")
public class UserAssociationController {

    @Autowired
    private AssociationService associationService;
    @Autowired
    private AssociationResourceAssembler associationResourceAssembler;


    @GetMapping("/get/association/{id}")
    public Resource<AssociationModel> oneAssociation(@PathVariable Integer id) {

        AssociationModel association = associationService.findById(id)
                .orElseThrow(() -> new AssociationNotFoundException(id));

        return associationResourceAssembler.toResource(association);
    }

    @GetMapping("/get/association")
    public Resources<Resource<AssociationModel>> allAssociations() {

        List<Resource<AssociationModel>> associations = associationService.findAll()
                .stream()
                .map(associationResourceAssembler::toResource)
                .collect(Collectors.toList());

        return new Resources<>(associations,
                linkTo(methodOn(UserAssociationController.class).allAssociations()).withSelfRel());
    }
}
