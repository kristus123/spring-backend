package com.example.demo.assembler;

import com.example.demo.controllers.commonControllers.CommonAddressController;
import com.example.demo.models.AddressModel;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.ResourceAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.*;

@Component
public class AddressResourceAssembler implements ResourceAssembler<AddressModel, Resource<AddressModel>> {

    @Override
    public Resource<AddressModel> toResource(AddressModel address) {
        return new Resource<>(address,
                linkTo(methodOn(CommonAddressController.class).getAddress(address.getAddressId())).withSelfRel(),
                linkTo(methodOn(CommonAddressController.class).getAddresses()).withRel("addresses")
        );
    }
}