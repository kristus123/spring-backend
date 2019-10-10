package com.example.demo.controllers.adminControllers;

import com.example.demo.assembler.AssociationResourceAssembler;
import com.example.demo.models.AssociationModel;
import com.example.demo.services.AssociationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Resource;
import org.springframework.web.bind.annotation.*;

import java.net.URISyntaxException;
import java.util.Optional;

@RestController
@RequestMapping("/v1/admin")
public class AdministratorAssociationController {

    @Autowired
    private AssociationService associationService;
    @Autowired
    private AssociationResourceAssembler associationResourceAssembler;

    @PostMapping("/post/association")
    public Resource<AssociationModel> newAssociation(@RequestBody AssociationModel associationModel) throws URISyntaxException {

        Resource<AssociationModel> resource = associationResourceAssembler.toResource(associationService.save(associationModel));
        /*
        ResponseEntity
                .created(new URI(resource.getId().expand().getHref()))
                .body(resource);
         */

        return resource;
    }

    @PutMapping("/update/association/{id}")
    public Resource<AssociationModel> updateAssociation(@PathVariable Integer id, @RequestBody AssociationModel associationModel) {
        if (associationModel.getAssociationId() != id || !associationService.findById(id).isPresent()) {
            //ResponseEntity.badRequest().build();
            return null;
        }

        //return ResponseEntity.ok(associationService.save(associationModel));
        return associationResourceAssembler.toResource(associationService.save(associationModel));
    }

    @DeleteMapping("/admin/delete/association/{id}")
    public Resource<AssociationModel> deleteAssociation(@PathVariable Integer id) {
        Optional<AssociationModel> association = associationService.findById(id);
        if (!association.isPresent()) {
            //ResponseEntity.badRequest().build();
            return null;
        }

        associationService.deleteById(id);

        //return ResponseEntity.ok().build();
        return associationResourceAssembler.toResource(association.get());
    }
}
