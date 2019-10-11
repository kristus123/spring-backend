package com.example.demo.services;

import com.example.demo.models.*;
import com.example.demo.repositories.*;
import org.hibernate.event.spi.SaveOrUpdateEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.LocalDate;
import java.time.Month;
import java.util.List;

@Service
public class DummyDataService {
    //create everything necessary to have a complete match

    @Autowired TeamService teamService;

    @Autowired AssociationService associationService;

    @Autowired OwnerRepository ownerRepository;

    @Autowired PersonRepository personRepository;

    @Autowired PlayerRepository playerRepository;

    @Autowired CoachRepository coachRepository;

    @Autowired LocationRepository locationRepository;

    @Autowired
    AddressService addressService;

    @Autowired PersonService personService;

    @Autowired CoachService coachService;

    @Autowired TeamService teamModel;

    @Autowired OwnerService ownerService;

    @Autowired LocationService locationService;

    @Autowired PlayerService playerService;


    //Creates player and assigns to team
    public void createPlayer() {
        LocalDate date = LocalDate.of(2015, 2, 2);
        AddressModel address = addressService.createAddress(new AddressModel("489489", "OSLO", "SWEDEN", "ve30"));
        PersonModel person =  personService.create(new PersonModel("Alex", "Johansen", date, address));

        PlayerModel player = playerService.turnIntoPlayer(person);

        //player.setTeam();
        System.out.println("_____ASDASDA_SD_ASD_ASD________");
        //System.out.println(teamService.findAll());
        System.out.println("_____ASDASDA_SD_ASD_ASD________");
        player = playerService.save(player);

        //System.out.println(player.getTeam());
        player.setNormalPosition("BACK");
        player.setPlayerNumber("25");

        System.out.println(player.getPlayerNumber());
        System.out.println(player.getNormalPosition());




        //System.out.println("___asd__");
        //playerService.findAll().forEach(System.out::println);
        //System.out.println("___asd__");

    }

    public void TEST() {
        //en slags unit-test

        //Lag en standard person med
        AddressModel address = addressService.createAddress(new AddressModel("5306", "Erdal", "Norway", "Vestre 30"));
        PersonModel person = personService.create(new PersonModel("Kristian", "Solbakken", LocalDate.of(2018, Month.FEBRUARY, 1), address));
        System.out.println(person);

        //Kristian har lyst å bli en coach
        CoachModel coach = coachService.save(new CoachModel(person));
        System.out.println(coach);

        // Et lag må jo eksistere før man kan bli en rik fortballspiller



        // In the beginning there was manchester
        AssociationModel association = associationService.create(new AssociationModel("Manchester", "Best team"));

        TeamModel team = teamService.createTeam(association, null, null, null);

        // Kristian har lyst å bli eier av laget
        personService.makePersonOwnerOf(person, team);
        //kristian er også coachen
        team.setCoach(coach);

        LocationModel location = locationService.save(new LocationModel(address, "Macnhester stadium", "it's in Kristian's backyard"));
        team.setLocation(location);


        locationRepository.findAll().forEach(System.out::println);

        System.out.println("_____222____");
        personRepository.findAll().forEach(System.out::println);
        System.out.println("_________");
        coachRepository.findAll().forEach(System.out::println);

        ownerRepository.findAll().forEach(System.out::println);




    }

}


