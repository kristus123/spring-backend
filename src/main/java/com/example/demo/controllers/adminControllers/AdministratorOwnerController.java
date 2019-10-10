package com.example.demo.controllers.adminControllers;

import com.example.demo.models.OwnerModel;
import com.example.demo.services.OwnerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.security.acl.Owner;
import java.util.Optional;

@RestController @RequestMapping("/v1/admin")
public class AdministratorOwnerController {
    @Autowired
    OwnerService ownerService;

    @GetMapping("/get/owner/{ownerId}")
    public OwnerModel getOwner(@PathVariable int ownerId) {
        Optional<OwnerModel> ownerModel = ownerService.findById(ownerId);
        if(ownerModel.isPresent()) {
            return ownerModel.get();
        }
        return null;
    }

    @PostMapping("/post/owner")
    public OwnerModel addOwner(@RequestBody OwnerModel ownerModel) {
        OwnerModel newOwner = ownerService.save(ownerModel);
        return newOwner;
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
