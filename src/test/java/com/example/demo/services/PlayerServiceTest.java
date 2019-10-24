package com.example.demo.services;

import com.example.demo.dtos.*;
import com.example.demo.enums.ContactType;
import com.example.demo.models.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;
import java.time.Month;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class PlayerServiceTest {

    @Autowired
    PlayerService playerService;

    @Autowired AddressService addressService;
    @Autowired LocationService locationService;
    @Autowired PersonService personService;
    @Autowired ContactService contactService;
    @Autowired CoachService coachService;
    @Autowired OwnerService ownerService;
    @Autowired AssociationService associationService;
    @Autowired TeamService teamService;

    @BeforeEach
    void setup() {





    }

    @Test
    void testThatPlayerIsSaved() {

    }

    @Test
    void delete() {
    }

    @Test
    void findById() {
    }

    @Test
    void findAll() {
    }

    @Test
    void testSave() {
    }

    @Test
    void create() {
        PlayerModel saved = playerService.create(new PlayerDTO(1, 1, "Kristian Solbakken"));
        assertNotNull(saved);
    }

    //@Test
    void update() {

        AddressModel address = addressService.createAddress(new AddressModel("5306", "Erdal", "Norway", "Vestre 30"));
        LocationModel location = locationService.save(new LocationModel(address, "Macnhester stadium", "it's in Kristian's backyard"));

        PersonModel person = personService.create(new PersonModel("Kristian", "Solbakken", LocalDate.of(2018, Month.FEBRUARY, 1), address));
        contactService.create(new ContactDTO(person.getPersonId(), ContactType.EMAIL, "panda@panda.com"));

        CoachModel coach = coachService.create(new CoachDTO(person.getPersonId()));
        OwnerModel owner = ownerService.create(new OwnerDTO(person.getPersonId()));

        AssociationModel association = associationService.create(new AssociationModel("Manchester", "Best team"));
        TeamModel homeTeam = teamService.create(new TeamDTO(association.getAssociationId(), coach.getCoachId(), owner.getOwnerId(), location.getLocationId()));


        address = addressService.createAddress(new AddressModel("489489", "OSLO", "SWEDEN", "ve30"));
        location = locationService.save(new LocationModel(address, "Bislett stadion", "ved bislett kebab"));

        person =  personService.create(new PersonModel("Alex", "Johansen", LocalDate.of(2015, 2, 2), address));
        contactService.create(new ContactDTO(person.getPersonId(), ContactType.PHONE, "21212121"));

        association = associationService.create(new AssociationModel("Juventus", "Better than best team"));
        TeamModel awayTeam = teamService.create(new TeamDTO(association.getAssociationId(), coach.getCoachId(), owner.getOwnerId(), location.getLocationId()));




        PlayerModel saved = playerService.create(new PlayerDTO(person.getPersonId(), homeTeam.getTeamId(), "Kristian Solbakken"));
        PlayerModel updated = playerService.update(saved.getPlayerId(), new PlayerDTO(person.getPersonId(), awayTeam.getTeamId(), "Kristian Solbakken"));

        assertNotNull(updated);

    }

    @Test
    void deleteById() {
    }

    @Test
    void testFindById() {
    }

    @Test
    void findAllActive() {
    }

    @Test
    void findByIdForced() {
    }

    @Test
    void findAllForced() {
    }
}