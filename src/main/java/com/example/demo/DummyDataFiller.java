package com.example.demo;

import com.example.demo.dtos.*;
import com.example.demo.enums.ContactType;
import com.example.demo.enums.GoalType;
import com.example.demo.models.*;
import com.example.demo.services.*;
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
                                          PlayerService playerService,
                                          ContactService contactService,
                                          SeasonService seasonService,
                                          OwnerService ownerService,
                                          MatchService matchService,
                                          MatchGoalService matchGoalService) {
        return args -> {


            //Lag en standard person med
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

            PlayerModel player = playerService.create(new PlayerDTO(person.getPersonId(), homeTeam.getTeamId(), person.getFirstName()+" "+person.getLastName()));
            player.setNormalPosition("BACK");
            player.setPlayerNumber("25");
            playerService.save(player);

            association = associationService.create(new AssociationModel("Juventus", "Better than best team"));
            TeamModel awayTeam = teamService.create(new TeamDTO(association.getAssociationId(), coach.getCoachId(), owner.getOwnerId(), location.getLocationId()));

            person =  personService.create(new PersonModel("Ole", "Dole", LocalDate.of(2020, 2, 2), address));
            player = playerService.create(new PlayerDTO(person.getPersonId(), awayTeam.getTeamId(), person.getFirstName()+" "+person.getLastName()));

            SeasonModel season = seasonService.save(new SeasonModel(LocalDate.of(2000, Month.JANUARY, 1), LocalDate.of(2005, Month.JANUARY, 1), "S1E2", "good times"));
            MatchModel match = matchService.create(new MatchDTO(LocalDate.of(2019, Month.DECEMBER, 24), homeTeam.getTeamId(), awayTeam.getTeamId(), season.getSeasonId(), location.getLocationId()));

            MatchGoalModel goal = matchGoalService.create(new MatchGoalDTO(player.getPlayerId(), GoalType.SCORPION_KICK, match.getMatchId()));
            System.out.println(
                    "goal="+goal.getGoalId()+
                    "\nplayer="+goal.getPlayer().getPlayerId()+
                    "\nmatch="+goal.getMatch().getMatchId());

        };
    }
}

/**
 * OBSERVATION:
 * - every time I create a player, the playerId is previousPlayerId+=2... why??
 */
