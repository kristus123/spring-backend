package com.example.demo.controllers.adminControllers;

import com.example.demo.dtos.LocationDTO;
import com.example.demo.models.AddressModel;
import com.example.demo.models.LocationModel;
import com.example.demo.services.AddressService;
import com.example.demo.services.LocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController @RequestMapping("/v1/admin")
public class AdministratorLocationController {
    @Autowired
    private LocationService  locationService;

    @Autowired
    AddressService addressService;



    @GetMapping("/get/location")
    public List<LocationModel> getAll() {
        return locationService.findAll();
    }

    @GetMapping("/get/location/{locationId}")
    public LocationModel getAll(@PathVariable int locationId) {
        Optional<LocationModel> location = locationService.findById(locationId);
        if (location.isPresent()) return location.get();
        return null;
    }

    @PostMapping("/add/location") //Logikken her burde bli puttet inn i en service, but for now tshi will do
    public LocationModel save(@RequestBody LocationDTO locationDTO) {
        if (locationDTO.getAddressId().isPresent()) {
            Optional<AddressModel> address = addressService.findById(locationDTO.getAddressId().get());
            if (address.isPresent()) {
                System.out.println("fant addresse");
                locationDTO.getLocationModel().setAddress(address.get());
            }
        }
        return locationService.save(locationDTO.getLocationModel());
    }

}
