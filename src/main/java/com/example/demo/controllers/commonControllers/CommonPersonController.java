package com.example.demo.controllers.commonControllers;

import com.example.demo.models.PersonModel;
import com.example.demo.services.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/v1/common")
public class CommonPersonController {

    @Autowired

    PersonService personService;

    @GetMapping("/get/person/{id}")
    public PersonModel getPerson(@PathVariable Integer id) {

        Optional<PersonModel> person = personService.findById(id);
        if(!person.isPresent())
            return null;

        return person.get();
    }

    @GetMapping("/get/person")
    public List<PersonModel> getPeople() {

        List<PersonModel> people = personService.findAll();

        if (people.isEmpty())
            return null;

        return people;
    }
}