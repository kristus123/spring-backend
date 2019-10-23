package com.example.demo.assembler;

import com.example.demo.controllers.commonControllers.CommonAddressController;
import com.example.demo.controllers.commonControllers.CommonLocationController;
import com.example.demo.models.LocationModel;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.ResourceAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.*;

@Component
public class LocationResourceAssembler implements ResourceAssembler<LocationModel, Resource<LocationModel>> {

    @Override
    public Resource<LocationModel> toResource(LocationModel location) {
        return new Resource<>(location,
                linkTo(methodOn(CommonLocationController.class).getLocation(location.getLocationId())).withSelfRel(),
                linkTo(methodOn(CommonLocationController.class).getLocations()).withRel("locations"),
                linkTo(methodOn(CommonAddressController.class).getAddress(location.getAddress().getAddressId())).withRel("address")
        );
    }
}