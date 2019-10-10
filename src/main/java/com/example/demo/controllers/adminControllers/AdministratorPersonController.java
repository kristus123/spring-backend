package com.example.demo.controllers.adminControllers;

import com.example.demo.exceptions.PersonNotFoundException;
import com.example.demo.models.PersonModel;
import com.example.demo.repositories.PersonRepository;
import com.example.demo.services.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/v1/admin")
public class AdministratorPersonController {

    @Autowired
    PersonService personService;

    @GetMapping("/get/person/{id}")
    public PersonModel getPlayer(@PathVariable Integer id) {
        PersonModel personModel = personService.findById(id).orElseThrow(() -> new PersonNotFoundException(id));
        return personModel;
    }

    @PostMapping("/post/person")
    public PersonModel createPerson(@RequestBody PersonModel person) {
        return personService.save(person);
    }

    @PutMapping("/update/person/{id}")
    public PersonModel updatePerson(@PathVariable Integer id, @RequestBody PersonModel personModel) {
        return personService.update(id, personModel);
    }

    @DeleteMapping("/delete/person/{id}")
    public void deletePerson(@PathVariable Integer id) {
        personService.delete(id);
    }
}
