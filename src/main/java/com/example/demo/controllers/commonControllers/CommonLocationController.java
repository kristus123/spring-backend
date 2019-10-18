package com.example.demo.controllers.commonControllers;

import com.example.demo.models.LocationModel;
import com.example.demo.services.LocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/v1/common")
public class CommonLocationController {

    @Autowired
    LocationService locationService;

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
}
