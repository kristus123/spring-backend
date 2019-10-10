package com.example.demo.controllers.adminControllers;

import com.example.demo.models.AssociationModel;
import com.example.demo.services.AssociationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/v1/admin")
public class AdministratorAssociationController {

    @Autowired
    private AssociationService associationService;

    @PostMapping("/post/association")
    public AssociationModel newAssociation(@RequestBody AssociationModel associationModel) {
        return associationService.save(associationModel);
    }

    @PutMapping("/update/association/{id}")
    public AssociationModel updateAssociation(@PathVariable Integer id, @RequestBody AssociationModel associationModel) {
        if (associationModel.getAssociationId() != id || !associationService.findById(id).isPresent()) {
            return null;
        }

        return associationService.save(associationModel);
    }

    @DeleteMapping("/admin/delete/association/{id}")
    public AssociationModel deleteAssociation(@PathVariable Integer id) {
        Optional<AssociationModel> association = associationService.findById(id);
        if (!association.isPresent()) {
            return null;
        }

        associationService.deleteById(id);

        return association.get();
    }
}
