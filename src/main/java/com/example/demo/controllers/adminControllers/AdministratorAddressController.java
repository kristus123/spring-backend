package com.example.demo.controllers.adminControllers;

import com.example.demo.models.AddressModel;
import com.example.demo.services.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController @RequestMapping("/v1/admin")
public class AdministratorAddressController {
    @Autowired
    AddressService addressService;


    @PostMapping("/post/address")
    public AddressModel createAddress(@RequestBody AddressModel addressModel) {
        return addressService.save(addressModel);

    }

    @PutMapping("/update/address")
    public AddressModel updateAddress(@RequestBody AddressModel address) {
        return addressService.save(address);
    }
}
