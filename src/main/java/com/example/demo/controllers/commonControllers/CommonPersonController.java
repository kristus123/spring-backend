package com.example.demo.controllers.commonControllers;

import com.example.demo.assembler.PersonResourceAssembler;
import com.example.demo.exceptions.ElementNotFoundException;
import com.example.demo.models.PersonModel;
import com.example.demo.services.PersonService;
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
public class CommonPersonController {

    @Autowired
    PersonService personService;

    @Autowired
    PersonResourceAssembler assembler;

    @GetMapping("/get/person/{id}")
    public ResponseEntity<Resource<PersonModel>> getPerson(@PathVariable Integer id) {

        PersonModel person = personService.findById(id)
                .orElseThrow(() -> new ElementNotFoundException("Could not find person with ID=" + id));

        Resource<PersonModel> resource = assembler.toResource(person);

        return ResponseEntity
                .ok(resource);
    }

    @GetMapping("/get/person")
    public ResponseEntity<Resources<Resource<PersonModel>>> getPeople() {

        List<Resource<PersonModel>> people = personService.findAll()
                .stream()
                .map(assembler::toResource)
                .collect(Collectors.toList());

        // TODO PANDA: throw exception or return ResponseEntity.ok()?
        if (people.isEmpty())
            throw new ElementNotFoundException("No people registered");

        return ResponseEntity
                .ok(new Resources<>(people,
                        linkTo(methodOn(CommonPersonController.class).getPeople()).withSelfRel()));
    }
}