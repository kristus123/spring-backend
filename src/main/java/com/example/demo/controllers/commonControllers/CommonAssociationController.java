package com.example.demo.controllers.commonControllers;

import com.example.demo.models.AssociationModel;
import com.example.demo.services.AssociationService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/v1/common")
public class CommonAssociationController {

    @Autowired
    private AssociationService associationService;


    @GetMapping("/get/association/{id}")
    public AssociationModel getAssociation(@PathVariable Integer id) {

        Optional<AssociationModel> association = associationService.findById(id);
        if (!association.isPresent())
            return null;

        return association.get();
    }

    @GetMapping("/get/association")
    public List<AssociationModel> getAssociations() {

        List<AssociationModel> associations = associationService.findAll();

        if (associations.isEmpty())
            return null;

        return associations;
    }
}
