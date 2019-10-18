package com.example.demo.controllers.commonControllers;

import com.example.demo.models.AddressModel;
import com.example.demo.services.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/v1/common")
public class CommonAddressController {


    @Autowired
    AddressService addressService;

    @GetMapping("/get/address")
    public List<AddressModel> getAll() {
        return addressService.findall();
    }

    @GetMapping("/get/address/{addressId}")
    public AddressModel findById(@PathVariable int addressId) {
        Optional<AddressModel> address = addressService.findById(addressId);
        if (address.isPresent()) return address.get();
        return null; //Oca syntax baby
    }
}
