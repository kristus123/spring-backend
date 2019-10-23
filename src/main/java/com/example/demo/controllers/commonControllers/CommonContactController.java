package com.example.demo.controllers.commonControllers;

import com.example.demo.assembler.ContactResourceAssembler;
import com.example.demo.exceptions.ElementNotFoundException;
import com.example.demo.models.ContactModel;
import com.example.demo.services.ContactService;
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
public class CommonContactController {

    @Autowired
    ContactService contactService;

    @Autowired
    ContactResourceAssembler assembler;

    @GetMapping("/get/contact/{id}")
    public ResponseEntity<Resource<ContactModel>> getContact(@PathVariable Integer id) {

        ContactModel contact = contactService.findById(id)
                .orElseThrow(() -> new ElementNotFoundException("Could not find contact with ID=" + id));

        Resource<ContactModel> resource = assembler.toResource(contact);

        return ResponseEntity
                .ok(resource);
    }

    @GetMapping("/get/contact")
    public ResponseEntity<Resources<Resource<ContactModel>>> getContacts() {

        List<Resource<ContactModel>> contacts = contactService.findAll()
                .stream()
                .map(assembler::toResource)
                .collect(Collectors.toList());

        // TODO PANDA: throw exception or return ResponseEntity.ok()?
        if (contacts.isEmpty())
            throw new ElementNotFoundException("No contacts registered");

        return ResponseEntity
                .ok(new Resources<>(contacts,
                        linkTo(methodOn(CommonContactController.class).getContacts()).withSelfRel()));
    }
}
