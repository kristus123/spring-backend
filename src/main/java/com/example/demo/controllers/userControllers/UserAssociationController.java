package com.example.demo.controllers.userControllers;

import com.example.demo.exceptions.AssociationNotFoundException;
import com.example.demo.models.AssociationModel;
import com.example.demo.services.AssociationService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/v1/user")
public class UserAssociationController {

    @Autowired
    private AssociationService associationService;


    @GetMapping("/get/association/{id}")
    public AssociationModel oneAssociation(@PathVariable Integer id) {
        return associationService.findById(id).get();
    }

    @GetMapping("/get/association")
    public List<AssociationModel> allAssociations() {

        List<AssociationModel> associations = associationService.findAll();

        return associations;
    }
}
