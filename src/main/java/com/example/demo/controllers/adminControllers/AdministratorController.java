package com.example.demo.controllers.adminControllers;

import com.example.demo.dtos.LocationDTO;
import com.example.demo.models.AddressModel;
import com.example.demo.models.LocationModel;
import com.example.demo.repositories.AddressRepository;
import com.example.demo.repositories.UserRepository;
import com.example.demo.services.CreationService;
import com.example.demo.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Optional;

@RestController @RequestMapping("/v1/admin")
public class AdministratorController {
    @Autowired
    UserService userService;

    @Autowired
    CreationService creationService;

    @Autowired
    AddressRepository addressRepository;



    @PutMapping("/giveUserStandard/{userId}")
    public boolean elevateUserToStandard(@PathVariable int userId) {
        return userService.elevateUserToStandard(userId);
    }


    @PutMapping("/giveUserAdmin/{userId}")
    public boolean elevateUserToAdmin(@PathVariable int userId) {
        return userService.elevateUserToAdmin(userId);
    }


    //Denne er en duplicate av -  /v1/admin/add/location
    @PostMapping("/createLocation")
    public LocationModel createLocation(@RequestBody LocationDTO locationDTO) {
        if (locationDTO.getAddressId().isPresent()) {
            Optional<AddressModel> address = addressRepository.findById(locationDTO.getAddressId().get());
            if (address.isPresent()) locationDTO.getLocationModel().setAddress(address.get());


        }
        System.out.println(locationDTO.getLocationModel().getAddress().getCity());
        return creationService.createLocation(locationDTO);
    }

    @PostMapping("/giveLocationAddress/{addressId}")
    public LocationModel giveLocationAddress(@RequestBody LocationModel locationModel, @PathVariable int addressId) {
        return creationService.assignAddressToLocation(locationModel, addressId);
    }

}
