package com.example.demo.assembler;

import com.example.demo.controllers.commonControllers.CommonAssociationController;
import com.example.demo.models.AssociationModel;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.*;

import org.springframework.hateoas.Resource;
import org.springframework.hateoas.ResourceAssembler;
import org.springframework.stereotype.Component;

@Component
public class AssociationResourceAssembler implements ResourceAssembler<AssociationModel, Resource<AssociationModel>> {

    @Override
    public Resource<AssociationModel> toResource(AssociationModel associationModel) {
        return new Resource<AssociationModel>(associationModel,
                linkTo(methodOn(CommonAssociationController.class).getAssociation(associationModel.getAssociationId())).withSelfRel(),
                linkTo(methodOn(CommonAssociationController.class).getAssociations()).withRel("associations"));
    }
}
