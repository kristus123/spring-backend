package com.example.demo.assembler;

import com.example.demo.controllers.commonControllers.CommonContactController;
import com.example.demo.controllers.commonControllers.CommonPersonController;
import com.example.demo.models.ContactModel;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.ResourceAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.*;

@Component
public class ContactResourceAssembler implements ResourceAssembler<ContactModel, Resource<ContactModel>> {

    @Override
    public Resource<ContactModel> toResource(ContactModel contact) {
        return new Resource<>(contact,
                linkTo(methodOn(CommonContactController.class).getContact(contact.getContactId())).withSelfRel(),
                linkTo(methodOn(CommonContactController.class).getContacts()).withRel("contacts"),
                linkTo(methodOn(CommonPersonController.class).getPerson(contact.getPerson().getPersonId())).withRel("person")
        );
    }
}