package com.example.demo.controllers.commonControllers;

import com.example.demo.assembler.OwnerResourceAssembler;
import com.example.demo.exceptions.ElementNotFoundException;
import com.example.demo.models.OwnerModel;
import com.example.demo.services.OwnerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;


@RestController
@RequestMapping("/v1/common")
public class CommonOwnerController {

    @Autowired
    OwnerService ownerService;

    @Autowired
    OwnerResourceAssembler assembler;

    @GetMapping("/get/owner/{id}")
    public ResponseEntity<Resource<OwnerModel>> getOwner(@PathVariable Integer id) {

        OwnerModel owner = ownerService.findById(id)
                .orElseThrow(() -> new ElementNotFoundException("Could not find owner with ID=" + id));

        Resource<OwnerModel> resource = assembler.toResource(owner);

        return ResponseEntity
                .ok(resource);
    }

    @GetMapping("/get/owner")
    public ResponseEntity<Resources<Resource<OwnerModel>>> getOwners() {

        List<Resource<OwnerModel>> owners = ownerService.findAll()
                .stream()
                .map(assembler::toResource)
                .collect(Collectors.toList());

        // TODO PANDA: throw exception or return ResponseEntity.ok()?
        if (owners.isEmpty())
            throw new ElementNotFoundException("No owners registered");

        return ResponseEntity
                .ok(new Resources<>(owners,
                        linkTo(methodOn(CommonOwnerController.class).getOwners()).withSelfRel()));
    }
}