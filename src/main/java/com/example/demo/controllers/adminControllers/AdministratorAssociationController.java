package com.example.demo.controllers.adminControllers;

import com.example.demo.assembler.AssociationResourceAssembler;
import com.example.demo.models.AssociationModel;
import com.example.demo.services.AssociationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Optional;

@RestController @RequestMapping("/v1/admin")
public class AdministratorAssociationController {
    @Autowired
    AssociationService associationService;

    @Autowired
    AssociationResourceAssembler assembler;


    @PostMapping("/post/association")
    public ResponseEntity<Resource<AssociationModel>> addAssociation(@RequestBody AssociationModel association) throws URISyntaxException  {

        AssociationModel teamModel = associationService.save(association);
        Resource<AssociationModel> resource = assembler.toResource(teamModel);

        return ResponseEntity
                .created(new URI(resource.getId().expand().getHref()))
                .body(resource);
    }

    @PutMapping("/update/association/{associationId}")
    public ResponseEntity<Resource> updateAssociation(@PathVariable int associationId, @RequestBody AssociationModel association) throws URISyntaxException {

        AssociationModel updated = associationService.update(associationId, association);
        Resource resource = assembler.toResource(updated);

        return ResponseEntity
                .created(new URI(resource.getId().expand().getHref()))
                .body(resource);
    }

    @DeleteMapping("/delete/association/{associationId}")
    public ResponseEntity<AssociationModel> deleteAssociation(@PathVariable int associationId) {
        AssociationModel association = associationService.deleteById(associationId);
        return ResponseEntity.ok(association);
    }
}
