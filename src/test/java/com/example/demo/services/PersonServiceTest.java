package com.example.demo.services;

import com.example.demo.models.*;
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

    @Autowired PlayerService playerService;


    @Test
    void testCreatePerson() {
        PersonModel person = new PersonModel("kristian", "lavik", LocalDate.of(2015, 1, 2));
        person.setAddress(new AddressModel("D", "C" ,"B" ,"A"));
        personService.save(person);

        int x = 5_______________________________________5;
        System.out.println(x);

        person = personRepository.findByFirstName("kristian").get();


        assertTrue(person.getAddress().getCountry().equals("B"));
    }


    @Test
    void makePersonPlayerOf() {
        int teamId = teamService.findAll().get(0).getTeamId();

        PlayerModel player =  personService.makePersonPlayerOf(teamId, 1);
        System.out.println(player.getPlayerId());

        TeamModel team = teamService.findById(teamId).get();

        assertTrue(playerService.findById(player.getPlayerId()).get().getTeam().getTeamId() == team.getTeamId());

    }




    @Test
    void makePersonOwnerOf(){
        PersonModel person = personService.findAll().get(0);
        // Let's make a new team instead
        TeamModel team = new TeamModel();
        //TeamModel team = teamService.findAll().get(0);

        team.setOwner(null);
        teamService.save(team);
        team = teamService.findById(team.getTeamId()).get();

        team = personService.makePersonOwnerOf(person, team);

        assertTrue(team.getOwner().getPerson().getFirstName().equals(person.getFirstName()));

        team = teamService.findById(team.getTeamId()).get();

        assertTrue(team.getOwner().getPerson().getFirstName().equals(person.getFirstName()));

        //team = teamService.findAll().get(0);





    }

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