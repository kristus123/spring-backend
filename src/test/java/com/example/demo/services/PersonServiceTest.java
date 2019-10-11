package com.example.demo.services;

import com.example.demo.models.AddressModel;
import com.example.demo.models.AssociationModel;
import com.example.demo.models.CoachModel;
import com.example.demo.models.PersonModel;
import com.example.demo.repositories.PersonRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class PersonServiceTest {

    @Autowired CoachService coachService;

    @Autowired
    PersonService personService;

    @Autowired PersonRepository personRepository;

    @Autowired TeamService teamService;

    @Autowired AddressService addressService;

    @Autowired AssociationService associationService;

    @Test
    void testDelete() {
        AddressModel address = addressService.createAddress(new AddressModel("BERGEN", "BERGEN", "BERGEN", "BERGEN"));
        PersonModel person =  personService.save(new PersonModel("Kristian", "Solbakken", LocalDate.of(2015, 10, 10), address));

        AssociationModel associationModel = associationService.create(new AssociationModel("Liverpool", "yey"));

        CoachModel coach = coachService.makePersonCoach(person);
        System.out.println(person.getPersonId());
        System.out.println("________");
        personService.delete(person.getPersonId());
        assertFalse(personService.findById(person.getPersonId()).isPresent());

    }

}