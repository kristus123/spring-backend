package com.example.demo.controllers.adminControllers;

import com.example.demo.assembler.LocationResourceAssembler;
import com.example.demo.dtos.LocationDTO;
import com.example.demo.models.AddressModel;
import com.example.demo.models.LocationModel;
import com.example.demo.services.AddressService;
import com.example.demo.services.LocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

@RestController @RequestMapping("/v1/admin")
public class AdministratorLocationController {

    @Autowired
    LocationService  locationService;

    @Autowired
    LocationResourceAssembler assembler;



    @PostMapping("/add/location") //Logikken her burde bli puttet inn i en service, but for now tshi will do
    public ResponseEntity<Resource<LocationModel>> save(@RequestBody LocationDTO locationDTO) throws URISyntaxException {

        LocationModel location = locationService.create(locationDTO);
        Resource<LocationModel> resource = assembler.toResource(location);

        return ResponseEntity
                .created(new URI(resource.getId().expand().getHref()))
                .body(resource);
    }

    @PutMapping("/update/location/{id}")
    public ResponseEntity<Resource> updateLocation(@PathVariable Integer id, @RequestBody LocationDTO location) throws URISyntaxException {

        LocationModel updated = locationService.update(id, location);
        Resource resource = assembler.toResource(updated);

        return ResponseEntity
                .created(new URI(resource.getId().expand().getHref()))
                .body(resource);
    }

    @DeleteMapping("/delete/location/{id}")
    public ResponseEntity<LocationModel> deleteLocation(@PathVariable Integer id) {
        LocationModel team = locationService.deleteById(id);
        return ResponseEntity.ok(team);
    }

}
