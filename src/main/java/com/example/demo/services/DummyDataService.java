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

@Service
public class DummyDataService {
    //create everything necessary to have a complete match

    @Autowired
    TeamService teamService;

    @Autowired
    AssociationService associationService;

    @Autowired
    OwnerRepository ownerRepository;

    @Autowired
    PersonRepository personRepository;

    @Autowired
    PlayerRepository playerRepository;


    @Autowired
    CoachRepository coachRepository;

    @Autowired
    LocationRepository locationRepository;

    public void createTeam() {
        AssociationModel associationModel = new AssociationModel("manchester club", "en fin klubb");

        AddressModel addressModel = new AddressModel("5306",  "BERGEN",  "NORGE",  "VESTRE 30");

        PersonModel personModel = new PersonModel("JON", "ARNE-RISE", LocalDate.of(2015, 2, 2),  addressModel);


        OwnerModel ownerModel = new OwnerModel(personModel);

        LocationModel locationModel = new LocationModel(addressModel, "berkely stadium", "et fint sted");


        CoachModel coachModel = new CoachModel(personModel);

        teamService.save(new TeamModel(associationModel, coachModel, ownerModel, locationModel));

    }

    public void insertPlayerToTeam() {
        AddressModel addressModel = new AddressModel("5306", "BERGEN", "NORGE", "Vestre 20");
        PersonModel personModel = new PersonModel("kristian", "solbakken", LocalDate.of(1999, 2,1 ), addressModel);


        System.out.println("ASSOSIONSERVICE : : :  " + associationService.findAll().get(0));

        TeamModel teamModel = new TeamModel(
                new AssociationModel("barcelona", "fint lag"),
                new CoachModel(personModel), //coachRepository.findAll().get(0) ,
                new OwnerModel(personModel),
                new LocationModel(
                        new AddressModel("aa", "b", "c", "d"), "asdk", "asdko"));

        System.out.println(teamModel);

        System.out.println(personModel);


        playerRepository.save(
            new PlayerModel(personModel, teamModel, "Defence", "5")
        );

        System.out.println("_______");
        playerRepository.findAll().forEach(p -> {
            System.out.println(p.getPerson());
            System.out.println(p.getPlayerNumber());
            System.out.println(p.getTeam().getAssociation().getName());
        });

        System.out.println("_______");



    }

}


