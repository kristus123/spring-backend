package com.example.demo.controllers.adminControllers;

import com.example.demo.assembler.OwnerResourceAssembler;
import com.example.demo.dtos.OwnerDTO;
import com.example.demo.exceptions.ElementNotFoundException;
import com.example.demo.models.CoachModel;
import com.example.demo.models.OwnerModel;
import com.example.demo.models.TeamModel;
import com.example.demo.services.CoachService;
import com.example.demo.services.OwnerService;
import com.example.demo.services.PersonService;
import com.example.demo.services.TeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.security.acl.Owner;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController @RequestMapping("/v1/admin")
public class AdministratorOwnerController {
    @Autowired OwnerService ownerService;
    @Autowired PersonService personService;
    @Autowired CoachService coachService;
    @Autowired TeamService teamService;
    @Autowired OwnerResourceAssembler assembler;

    @PostMapping("/post/owner")
    public OwnerModel addOwner(@RequestBody OwnerDTO owner) throws URISyntaxException {
        return ownerService.create(owner);
    }

    @PutMapping("/post/owner/assign-as-Owner-of-Team")
    public boolean makeOwnerOwnerOf(@RequestBody Map<String, Integer> request) {
        teamService.findById(request.get("ownerId"));
        ownerService.makeOwnerOf(request.get("ownerId"), request.get("teamId"));
        return true;
    }

    @GetMapping("/get/owner/{ownerId}/allTeams")
    public List<TeamModel> getAllTeamsOfOwner(@PathVariable int ownerId) {
        System.out.println(ownerService.findAllOwnedTeams(ownerId));
        return ownerService.findAllOwnedTeams(ownerId);
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
