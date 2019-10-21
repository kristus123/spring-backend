package com.example.demo.assembler;

import com.example.demo.controllers.commonControllers.CommonAddressController;
import com.example.demo.controllers.commonControllers.CommonPersonController;
import com.example.demo.models.PersonModel;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.ResourceAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.*;


@Component
public class PersonResourceAssembler implements ResourceAssembler<PersonModel, Resource<PersonModel>> {

    @Override
    public Resource<PersonModel> toResource(PersonModel person) {
        return new Resource<>(person,
                linkTo(methodOn(CommonPersonController.class).getPerson(person.getPersonId())).withSelfRel(),
                linkTo(methodOn(CommonPersonController.class).getPeople()).withRel("people"),
                linkTo(methodOn(CommonAddressController.class).getAddress(person.getAddress().getAddressId())).withRel("address")
        );
    }
}