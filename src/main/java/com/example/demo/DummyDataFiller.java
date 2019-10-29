package com.example.demo;

import com.example.demo.dtos.*;
import com.example.demo.enums.ContactType;
import com.example.demo.enums.GoalType;
import com.example.demo.enums.UserRole;
import com.example.demo.models.*;
import com.example.demo.repositories.PlayerRepository;
import com.example.demo.repositories.TeamRepository;
import com.example.demo.repositories.UserRepository;
import com.example.demo.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.Month;


@Component
@Profile("!test")
public class DummyDataFiller implements CommandLineRunner {

    @Autowired
    DummyDataService dummyDataService;

    @Autowired
    TeamRepository teamRepository;

    @Autowired
    PlayerRepository playerRepository;

    @Autowired
    UserService userService;


    @Override
    public void run(String...args) throws Exception {
        //userRepository.save(new UserModel("krispetter@gmail.com", "" ,"ADMINISTRATOR"))
        userService.signup("krispetter@gmail.com", "krispetter@gmail.com", UserRole.ADMINISTRATOR);

        // ------------------ CREATING TEAMS ------------------ //
        TeamModel team_1 = dummyDataService.createTeam("Barcelona", "Camp Nou", "persons_barcelona");
        TeamModel team_2 =  dummyDataService.createTeam("Real Madrid", "Estadio Santiago Bernabéu", "persons_madrid");

        // ------------------ CREATING MATCH ------------------ //
        SeasonModel season = new SeasonModel(LocalDate.of(2015, 01, 01),
                LocalDate.of(2020, 01,01),
                "La Liga",
                "Top division in the spanish league. Renowned for its acting");

        SeasonModel season_2 = new SeasonModel(LocalDate.of(2015, 01, 01),
                LocalDate.of(2020, 01,01),
                "La Liga 2",
                "Top division in the spanish league. Renowned for its acting");

        dummyDataService.createMatch(season, team_1, team_2, "matchGoals_1");
        dummyDataService.createMatch(season_2, team_1, team_2, "matchGoals_1");

    }
}