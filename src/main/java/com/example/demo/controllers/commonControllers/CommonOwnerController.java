package com.example.demo.controllers.commonControllers;

import com.example.demo.models.OwnerModel;
import com.example.demo.services.OwnerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/v1/common")
public class CommonOwnerController {

    @Autowired
    OwnerService ownerService;

    @GetMapping("/get/owner/{id}")
    public OwnerModel getOwner(@PathVariable Integer id) {

        Optional<OwnerModel> owner = ownerService.findById(id);
        if(!owner.isPresent())
            return null;

        return owner.get();
    }

    @GetMapping("/get/owner")
    public List<OwnerModel> getOwners() {

        List<OwnerModel> owners = ownerService.findAll();

        if (owners.isEmpty())
            return null;

        return owners;
    }
}