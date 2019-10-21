package com.example.demo.controllers.adminControllers;

import com.example.demo.assembler.PersonResourceAssembler;
import com.example.demo.dtos.PersonDTO;
import com.example.demo.models.CoachModel;
import com.example.demo.models.PersonModel;
import com.example.demo.models.PlayerModel;
import com.example.demo.models.TeamModel;
import com.example.demo.services.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

@RestController
@RequestMapping("/v1/admin")
public class AdministratorPersonController {

    @Autowired
    PersonService personService;

    @Autowired
    PersonResourceAssembler assembler;


    @PostMapping("/post/person")
    public ResponseEntity<Resource<PersonModel>> addPerson(@RequestBody PersonDTO dto) throws URISyntaxException {
        PersonModel person = personService.create(dto);
        Resource<PersonModel> resource = assembler.toResource(person);

        return ResponseEntity
                .created(new URI(resource.getId().expand().getHref()))
                .body(resource);
    }

    @PutMapping("/update/person/{id}")
    public ResponseEntity<Resource> updatePerson(@PathVariable Integer id, @RequestBody PersonDTO person) throws URISyntaxException {

        PersonModel updated = personService.update(id, person);
        Resource resource = assembler.toResource(updated);

        return ResponseEntity
                .created(new URI(resource.getId().expand().getHref()))
                .body(resource);
    }

    @DeleteMapping("/delete/person/{id}")
    public ResponseEntity<PersonModel> deletePerson(@PathVariable Integer id) {
        PersonModel person = personService.deleteById(id);
        return ResponseEntity.ok(person);
    }


    //Har laget unit tester for servicen - Kristian
    @PutMapping("/person/makeCoach")
    public ResponseEntity<CoachModel> makePersonCoach(@RequestParam int personId, @RequestParam int teamID) {

        return ResponseEntity.ok(personService.makePersonCoachOf(personId, teamID));

    }


    @PutMapping("/person/makeOwner")
    public ResponseEntity<TeamModel> makePersonOwner(@RequestParam int personId, @RequestParam int teamID) {
        return ResponseEntity.ok(personService.makePersonOwnerOf(personId, teamID));
    }

    @PutMapping("/person/makePlayer")
    public ResponseEntity<PlayerModel> makePersonPlayer(@RequestParam int personId, @RequestParam int teamId) {
        return ResponseEntity.ok(personService.makePersonPlayerOf(personId, teamId));
    }


}
