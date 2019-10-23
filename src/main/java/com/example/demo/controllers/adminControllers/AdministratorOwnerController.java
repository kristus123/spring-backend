package com.example.demo.controllers.adminControllers;

import com.example.demo.assembler.OwnerResourceAssembler;
import com.example.demo.dtos.OwnerDTO;
import com.example.demo.models.OwnerModel;
import com.example.demo.services.OwnerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.security.acl.Owner;
import java.util.Optional;

@RestController @RequestMapping("/v1/admin")
public class AdministratorOwnerController {
    @Autowired
    OwnerService ownerService;

    @Autowired
    OwnerResourceAssembler assembler;


    @PostMapping("/post/owner")
    public ResponseEntity<Resource<OwnerModel>> addOwner(@RequestBody OwnerDTO owner) throws URISyntaxException {

        OwnerModel teamModel = ownerService.create(owner);
        Resource<OwnerModel> resource = assembler.toResource(teamModel);

        return ResponseEntity
                .created(new URI(resource.getId().expand().getHref()))
                .body(resource);
    }

    @PutMapping("/update/owner/{ownerId}")
    public ResponseEntity<Resource> updateOwner(@PathVariable int ownerId, @RequestBody OwnerDTO owner) throws URISyntaxException {

        OwnerModel updated = ownerService.update(ownerId, owner);
        Resource resource = assembler.toResource(updated);

        return ResponseEntity
                .created(new URI(resource.getId().expand().getHref()))
                .body(resource);
    }

    @DeleteMapping("/delete/owner/{ownerId}")
    public ResponseEntity<OwnerModel> deleteOwner(@PathVariable int ownerId) {
        OwnerModel owner = ownerService.deleteById(ownerId);
        return ResponseEntity.ok(owner);
    }
}
