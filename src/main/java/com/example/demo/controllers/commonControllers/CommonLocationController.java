package com.example.demo.controllers.commonControllers;

import com.example.demo.assembler.LocationResourceAssembler;
import com.example.demo.exceptions.ElementNotFoundException;
import com.example.demo.models.LocationModel;
import com.example.demo.services.LocationService;
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
public class CommonLocationController {

    @Autowired
    LocationService locationService;

    @Autowired
    LocationResourceAssembler assembler;


    @GetMapping("/get/location/{id}")
    public ResponseEntity<Resource<LocationModel>> getLocation(@PathVariable Integer id) {

        LocationModel location = locationService.findById(id)
                .orElseThrow(() -> new ElementNotFoundException("Could not find location with ID=" + id));

        Resource<LocationModel> resource = assembler.toResource(location);

        return ResponseEntity
                .ok(resource);
    }

    @GetMapping("/get/location")
    public ResponseEntity<Resources<Resource<LocationModel>>> getLocations() {
        List<Resource<LocationModel>> locations = locationService.findAll()
                .stream()
                .map(assembler::toResource)
                .collect(Collectors.toList());

        // TODO PANDA: throw exception or return ResponseEntity.ok()?
        if (locations.isEmpty())
            throw new ElementNotFoundException("No locations registered");

        return ResponseEntity
                .ok(new Resources<>(locations,
                        linkTo(methodOn(CommonLocationController.class).getLocations()).withSelfRel()));
    }
}
