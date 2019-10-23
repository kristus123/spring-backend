package com.example.demo.controllers.commonControllers;

import com.example.demo.assembler.AddressResourceAssembler;
import com.example.demo.exceptions.ElementNotFoundException;
import com.example.demo.models.AddressModel;
import com.example.demo.services.AddressService;
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
public class CommonAddressController {


    @Autowired
    AddressService addressService;

    @Autowired
    AddressResourceAssembler assembler;

    @GetMapping("/get/address/{id}")
    public ResponseEntity<Resource<AddressModel>> getAddress(@PathVariable Integer id) {

        AddressModel address = addressService.findById(id)
                .orElseThrow(() -> new ElementNotFoundException("Could not find address with ID=" + id));

        Resource<AddressModel> resource = assembler.toResource(address);

        return ResponseEntity
                .ok(resource);
    }

    @GetMapping("/get/address")
    public ResponseEntity<Resources<Resource<AddressModel>>> getAddresses() {
        List<Resource<AddressModel>> addresses = addressService.findAll()
                .stream()
                .map(assembler::toResource)
                .collect(Collectors.toList());

        // TODO PANDA: throw exception or return ResponseEntity.ok()?
        if (addresses.isEmpty())
            throw new ElementNotFoundException("No addresses registered");

        return ResponseEntity
                .ok(new Resources<>(addresses,
                        linkTo(methodOn(CommonAddressController.class).getAddresses()).withSelfRel()));
    }

    @GetMapping("/get/address/{addressId}")
    public AddressModel findById(@PathVariable int addressId) {
        Optional<AddressModel> address = addressService.findById(addressId);
        if (address.isPresent()) return address.get();
        return null; //Oca syntax baby
    }
}
