package com.example.demo.controllers;

import com.example.demo.assembler.AssociationResourceAssembler;
import com.example.demo.exceptions.AssociationNotFoundException;
import com.example.demo.models.AssociationModel;
import com.example.demo.services.AssociationService;
import org.springframework.beans.factory.annotation.Autowired;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.*;

import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/v1")
public class AssociationController {

    @Autowired
    private AssociationService associationService;
    @Autowired
    private AssociationResourceAssembler associationResourceAssembler;


    @PostMapping("/admin/post/association")
    public Resource<AssociationModel> newAssociation(@RequestBody AssociationModel associationModel) throws URISyntaxException {

        Resource<AssociationModel> resource = associationResourceAssembler.toResource(associationService.save(associationModel));
        /*
        ResponseEntity
                .created(new URI(resource.getId().expand().getHref()))
                .body(resource);
         */

        return resource;
    }

    @PutMapping("/admin/update/association/{id}")
    public Resource<AssociationModel> updateAssociation(@PathVariable Integer id, @RequestBody AssociationModel associationModel) {
        if (!associationService.findById(id).isPresent()) {
            //ResponseEntity.badRequest().build();
        }

        // TODO PANDA: check IDs between the two instances??

        //return ResponseEntity.ok(associationService.save(associationModel));
        return associationResourceAssembler.toResource(associationService.save(associationModel));
    }

    @DeleteMapping("/admin/delete/association/{id}")
    public Resource<AssociationModel> deleteAssociation(@PathVariable Integer id) {
        Optional<AssociationModel> association = associationService.findById(id);
        if (!association.isPresent()) {
            //ResponseEntity.badRequest().build();
        }

        associationService.deleteById(id);

        //return ResponseEntity.ok().build();
        return associationResourceAssembler.toResource(association.get());
    }


    @GetMapping("/user/get/association/{id}")
    public Resource<AssociationModel> oneAssociation(@PathVariable Integer id) {

        AssociationModel association = associationService.findById(id)
                .orElseThrow(() -> new AssociationNotFoundException(id));

        return associationResourceAssembler.toResource(association);
    }

    @GetMapping("/user/get/associations")
    public Resources<Resource<AssociationModel>> allAssociations() {

        List<Resource<AssociationModel>> associations = associationService.findAll()
                .stream()
                .map(associationResourceAssembler::toResource)
                .collect(Collectors.toList());

        return new Resources<>(associations,
                linkTo(methodOn(AssociationController.class).allAssociations()).withSelfRel());
    }
}
