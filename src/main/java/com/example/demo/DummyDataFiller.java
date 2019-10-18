package com.example.demo;

import com.example.demo.models.PlayerModel;
import com.example.demo.models.SeasonModel;
import com.example.demo.models.TeamModel;
import com.example.demo.repositories.PlayerRepository;
import com.example.demo.repositories.TeamRepository;
import com.example.demo.services.DummyDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.time.LocalDate;


@Component
@Profile("!test")
public class DummyDataFiller implements CommandLineRunner {

    @Autowired
    DummyDataService dummyDataService;

    @Autowired
    TeamRepository teamRepository;

    @Autowired
    PlayerRepository playerRepository;

    @Override
    public void run(String...args) throws Exception {
        // ------------------ CREATING TEAMS ------------------ //
        TeamModel team_1 = dummyDataService.createTeam("Barcelona", "Camp Nou", "persons_barcelona");
        TeamModel team_2 =  dummyDataService.createTeam("Real Madrid", "Estadio Santiago Bernabéu", "persons_madrid");

        // ------------------ CREATING MATCH ------------------ //
        SeasonModel season = new SeasonModel(LocalDate.of(2015, 01, 01),
                LocalDate.of(2016, 01,01),
                "La Liga",
                "Top division in the spanish league. Renowned for its acting");

        dummyDataService.createMatch(season, team_1, team_2, "matchGoals_1");

    }
}