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

    /* TODO PANDA: moved to user controller
    @GetMapping("/get/association/{associationId}")
    public AssociationModel getAssociation(@PathVariable int associationId) {
        Optional<AssociationModel> associationModel = associationService.findById(associationId);
        if(associationModel.isPresent()) {
            return associationModel.get();
        }
        return null;
    }

     */

    @PostMapping("/post/association")
    public AssociationModel addAssociation(@RequestBody AssociationModel associationModel) {
        AssociationModel newAssociation = associationService.save(associationModel);
        return newAssociation;
    }

    @PutMapping("/update/association/{associationId}")
    public AssociationModel updateAssociation(@PathVariable int associationId, @RequestBody AssociationModel associationModel) {
        if (associationModel == null || associationId != associationModel.getAssociationId()) {
            return null;
        }

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
