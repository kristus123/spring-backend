package com.example.demo.assembler;

import com.example.demo.models.AssociationModel;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.ResourceAssembler;
import org.springframework.stereotype.Component;

@Component
public class AssociationResourceAssembler implements ResourceAssembler<AssociationModel, Resource<AssociationModel>> {

    @Override
    public Resource<AssociationModel> toResource(AssociationModel associationModel) {
        return new Resource<AssociationModel>(associationModel);
    }
}
