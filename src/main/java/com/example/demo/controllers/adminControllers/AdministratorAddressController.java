package com.example.demo.controllers.adminControllers;

import com.example.demo.assembler.AddressResourceAssembler;
import com.example.demo.models.AddressModel;
import com.example.demo.services.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

@RestController @RequestMapping("/v1/admin")
public class AdministratorAddressController {
    @Autowired
    AddressService addressService;

    @Autowired
    AddressResourceAssembler assembler;


    @PostMapping("/post/address")
    public ResponseEntity<Resource<AddressModel>> addAddress(@RequestBody AddressModel address) throws URISyntaxException {

        AddressModel teamModel = addressService.save(address);
        Resource<AddressModel> resource = assembler.toResource(teamModel);

        return ResponseEntity
                .created(new URI(resource.getId().expand().getHref()))
                .body(resource);

    }

    @PutMapping("/update/address/{id}")
    public ResponseEntity<Resource> updateAddress(@PathVariable Integer id, @RequestBody AddressModel address) throws URISyntaxException {

        AddressModel updated = addressService.update(id, address);
        Resource resource = assembler.toResource(updated);

        return ResponseEntity
                .created(new URI(resource.getId().expand().getHref()))
                .body(resource);
    }

    @DeleteMapping("/delete/season/{id}")
    public ResponseEntity<AddressModel> deleteMatch(@PathVariable Integer id) {
        AddressModel address = addressService.deleteById(id);
        return ResponseEntity.ok(address);
    }

}
