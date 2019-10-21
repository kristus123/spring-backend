package com.example.demo;

import com.example.demo.models.*;
import com.example.demo.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;
import java.time.Month;


@Configuration
public class DummyDataFiller {


    @Bean
    public CommandLineRunner initDatabase(TeamService teamService,
                                          AssociationService associationService,
                                          AddressService addressService,
                                          PersonService personService,
                                          CoachService coachService,
                                          LocationService locationService,
                                          PlayerService playerService) {
        return args -> {

            //Lag en standard person med
            AddressModel address = addressService.createAddress(new AddressModel("5306", "Erdal", "Norway", "Vestre 30"));
            PersonModel person = personService.create(new PersonModel("Kristian", "Solbakken", LocalDate.of(2018, Month.FEBRUARY, 1), address));

            //Kristian har lyst å bli en coach
            CoachModel coach = coachService.save(new CoachModel(person));

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


            LocalDate date = LocalDate.of(2015, 2, 2);
            AddressModel address2 = addressService.createAddress(new AddressModel("489489", "OSLO", "SWEDEN", "ve30"));
            PersonModel person2 =  personService.create(new PersonModel("Alex", "Johansen", date, address2));

            PlayerModel player = playerService.turnIntoPlayer(person2);
            player.setTeam(team);
            player.setNormalPosition("BACK");
            player.setPlayerNumber("25");

            player = playerService.save(player);


            // TODO PANDA

            associationService.create(new AssociationModel("Juventus", "Better than best team"));
            locationService.save(new LocationModel(address2, "Bislett stadion", "ved bislett kebab"));

        };
    }
}
