package com.example.demo.services;

import com.example.demo.models.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureWebMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
@ExtendWith(SpringExtension.class)
@AutoConfigureWebMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class HumanServiceTest {
    @Autowired HumanService humanService;
    @Autowired PersonService personService;
    @Autowired TeamService teamService;
    @Autowired AddressService addressService;
    @Autowired AssociationService associationService;
    @Autowired CoachService coachService;

    private static int personId;
    private static int teamId;

    @BeforeAll
    void beforeAll() {
        AddressModel address = addressService.createAddress(new AddressModel("5306" ,"Bergen", "Norge", "Vestre Vardane 30"));
        PersonModel person = personService.save(new PersonModel( "Kristian","Solbakken", LocalDate.of(2015, 2, 1), address));
        personId = person.getPersonId();
        AssociationModel association = associationService.save(new AssociationModel("Manchester", "NOT UNITED"));
        teamId = teamService.save(new TeamModel(association, null ,null, null)).getTeamId();
        //CoachModel coach = coachService.makePersonCoach(person);
        personService.makePersonCoachOf(person.getPersonId(), teamId);
    }


    @Test @Disabled
    void delete() {
        Optional<CoachModel>  coach = coachService.findByPersonId(personId);
        assertTrue(coach.isPresent());
        if (coach.isPresent()) {
            System.out.println(coach.get());
            humanService.delete(coach.get());
        }

    }

}