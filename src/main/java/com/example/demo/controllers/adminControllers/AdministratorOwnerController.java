package com.example.demo.controllers.adminControllers;

import com.example.demo.dtos.OwnerModelDTO;
import com.example.demo.models.OwnerModel;
import com.example.demo.models.PersonModel;
import com.example.demo.models.TeamModel;
import com.example.demo.services.OwnerService;
import com.example.demo.services.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.security.acl.Owner;
import java.util.Optional;

@RestController @RequestMapping("/v1/admin")
public class AdministratorOwnerController {
    @Autowired
    OwnerService ownerService;

    @Autowired
    PersonService personService;

    @GetMapping("/get/owner/{ownerId}")
    public OwnerModel getOwner(@PathVariable int ownerId) {
        Optional<OwnerModel> ownerModel = ownerService.findById(ownerId);
        if(ownerModel.isPresent()) {
            return ownerModel.get();
        }
        return null;
    }

    @PostMapping("/post/owner")
    public TeamModel addOwner(@RequestBody OwnerModelDTO ownerModel) {
        System.out.println(ownerModel.getPersonId());
        return personService.makePersonOwnerOf(ownerModel.getPersonId(), ownerModel.getTeamId());
    }

    @PutMapping("/update/owner/{ownerId}")
    public OwnerModel updateOwner(@PathVariable int ownerId, @RequestBody OwnerModel ownerModel) {
        Optional<OwnerModel> oldOwner = ownerService.findById(ownerId);
        if(oldOwner.isPresent()) {
            OwnerModel updatedOwner = ownerService.update(ownerModel, ownerModel);
            return updatedOwner;
        }
        return null;
    }

    @DeleteMapping("/delete/owner/{ownerId}")
    public OwnerModel deleteOwner(@PathVariable int ownerId) {
        Optional<OwnerModel> owner = ownerService.findById(ownerId);
        if(owner.isPresent()) {
            OwnerModel tempOwner = owner.get();
            ownerService.delete(owner.get());
            return tempOwner;
        }
        return null;
    }
}
