package com.example.demo.controllers.adminControllers;

import com.example.demo.models.CoachModel;
import com.example.demo.models.PersonModel;
import com.example.demo.models.PlayerModel;
import com.example.demo.models.TeamModel;
import com.example.demo.services.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/admin")
public class AdministratorPersonController {

    @Autowired
    PersonService personService;


    @PostMapping("/post/person")
    public String createPerson(@RequestBody PersonModel person) {
        personService.save(person);

        return "added";
        //return personService.findAll().stream().filter(p -> p.getLastName().equals(person.getLastName())).findFirst();
    }

    @PutMapping("/update/person/{id}")
    public PersonModel updatePerson(@PathVariable Integer id, @RequestBody PersonModel personModel) {
        return personService.update(id, personModel);
    }

    @DeleteMapping("/delete/person/{id}")
    public void deletePerson(@PathVariable Integer id) {
        personService.delete(id);
    }


    //Har laget unit tester for servicen - Kristian
    @PutMapping("/person/makeCoach")
    public CoachModel makePersonCoach(@RequestParam int personId, @RequestParam int teamID) {

        return personService.makePersonCoachOf(personId, teamID);

    }


    @PutMapping("/person/makeOwner")
    public TeamModel makePersonOwner(@RequestParam int personId, @RequestParam int teamID) {
        return personService.makePersonOwnerOf(personId, teamID);
    }

    @PutMapping("/person/makePlayer")
    public PlayerModel makePersonPlayer(@RequestParam int personId, @RequestParam int teamId) {
        return personService.makePersonPlayerOf(personId, teamId);
    }


}
