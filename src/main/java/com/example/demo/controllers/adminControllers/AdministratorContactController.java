package com.example.demo.controllers.adminControllers;

import com.example.demo.assembler.ContactResourceAssembler;
import com.example.demo.dtos.ContactDTO;
import com.example.demo.models.ContactModel;
import com.example.demo.services.ContactService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@RestController
@RequestMapping("/v1/admin")
public class AdministratorContactController {

    @Autowired
    ContactService contactService;

    @Autowired
    ContactResourceAssembler assembler;


    @PostMapping("/post/contact")
    public ResponseEntity<Resource<ContactModel>> createContact(@RequestBody ContactDTO contact) throws URISyntaxException {

        ContactModel contactModel = contactService.create(contact);
        Resource<ContactModel> resource = assembler.toResource(contactModel);

        return ResponseEntity
                .created(new URI(resource.getId().expand().getHref()))
                .body(resource);

    }

    @PutMapping("/update/contact/{id}")
    public ResponseEntity<Resource> updateContact(@PathVariable Integer id, @RequestBody ContactDTO contact) throws URISyntaxException {

        ContactModel updated = contactService.update(id, contact);
        Resource resource = assembler.toResource(updated);

        return ResponseEntity
                .created(new URI(resource.getId().expand().getHref()))
                .body(resource);
    }

    @DeleteMapping("/delete/contact/{id}")
    public ResponseEntity<ContactModel> deleteContact(@PathVariable Integer id) {
        ContactModel contact = contactService.deleteById(id);
        return ResponseEntity.ok(contact);
    }

}