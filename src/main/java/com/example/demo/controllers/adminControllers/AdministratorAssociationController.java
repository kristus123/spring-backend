package com.example.demo.controllers.adminControllers;

import com.example.demo.models.AssociationModel;
import com.example.demo.services.AssociationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController @RequestMapping("/v1/admin")
public class AdministratorAssociationController {
    @Autowired
    AssociationService associationService;

    @GetMapping("/get/association/{associationId}")
    public AssociationModel getAssociation(@PathVariable int associationId) {
        Optional<AssociationModel> associationModel = associationService.findById(associationId);
        if(associationModel.isPresent()) {
            System.out.println("hei på deg!");
            return associationModel.get();
        }
        System.out.println("null wtf?!");
        return null;
    }

    @PostMapping("/post/association")
    public AssociationModel addAssociation(@RequestBody AssociationModel associationModel) {
        AssociationModel newAssociation = associationService.save(associationModel);
        return newAssociation;
    }

    @PutMapping("/update/association/{associationId}")
    public AssociationModel updateAssociation(@PathVariable int associationId, @RequestBody AssociationModel associationModel) {
        Optional<AssociationModel> oldAssociation = associationService.findById(associationId);
        if(oldAssociation.isPresent()) {
            AssociationModel updatedAssociation = associationService.update(associationModel, oldAssociation.get());
            return updatedAssociation;
        }

        return null;
    }

    @DeleteMapping("/delete/association/{associationId}")
    public AssociationModel deleteAssociation(@PathVariable int associationId) {
        Optional<AssociationModel> associationModel = associationService.findById(associationId);
        if(associationModel.isPresent()) {
            AssociationModel tempAssociation = associationModel.get();
            associationService.deleteById(associationModel.get().getAssociationId());
            return tempAssociation;
        }

        return null;
    }
}
