package com.example.demo.assembler;

import com.example.demo.controllers.commonControllers.CommonOwnerController;
import com.example.demo.controllers.commonControllers.CommonPersonController;
import com.example.demo.models.OwnerModel;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.ResourceAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.*;

@Component
public class OwnerResourceAssembler implements ResourceAssembler<OwnerModel, Resource<OwnerModel>> {

    @Override
    public Resource<OwnerModel> toResource(OwnerModel owner) {
        return new Resource<>(owner,
                linkTo(methodOn(CommonOwnerController.class).getOwner(owner.getOwnerId())).withSelfRel(),
                linkTo(methodOn(CommonOwnerController.class).getOwners()).withRel("owners"),
                linkTo(methodOn(CommonPersonController.class).getPerson(owner.getPerson().getPersonId())).withRel("person")
        );
    }
}