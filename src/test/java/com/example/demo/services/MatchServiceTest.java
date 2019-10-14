package com.example.demo.services;

import com.example.demo.enums.GoalType;
import com.example.demo.models.*;
import org.apache.tomcat.jni.Local;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureWebMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.util.List;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@AutoConfigureWebMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class MatchServiceTest {

    @Autowired private MatchService matchService;

    @Autowired private TeamService teamService;

    @Autowired AssociationService associationService;

    @Autowired PersonService personService;

    @Autowired AddressService addressService;

    @Autowired SeasonService seasonService;

    @Autowired LocationService locationService;

    @Autowired MatchGoalService matchGoalService;

    @Autowired PlayerService playerService;

    private static String teamName = "Manchester 'Junit5'd";
    private static String enemyTeamName = "Brann";
    private static int playerIdWithTeam;

    @BeforeAll
    void setUpInformation() {
        AssociationModel association = associationService.save(new AssociationModel(teamName, "YARR"));
        AddressModel address = addressService.createAddress(new AddressModel("55094", "Stockholm", "Sweden", "Joghaugen 30"));
        PersonModel person = personService.create(new PersonModel("Kristian", "Solbakken", LocalDate.now(), address ));

        TeamModel team  = new TeamModel(association);
        teamService.save(team);

        AssociationModel association2 = associationService.save(new AssociationModel(enemyTeamName, "YARRio"));
        TeamModel team2  = new TeamModel(association2);

        teamService.save(team2);



        team = teamService.findAll().stream().filter(t -> t.getAssociation().getName().equals(teamName)).findFirst().get();
        assertTrue(team.getAssociation().getName().equals(teamName));

        PlayerModel player = personService.makePersonPlayerOf(person, team);
        assertNotNull(player.getTeam());

        System.out.println(player.getPlayerId());
        playerIdWithTeam = player.getPlayerId();

        player = playerService.findById(player.getPlayerId()).get();

        assertNotNull(player.getTeam());


        assertTrue(player.getTeam().getAssociation().getName().equals(team.getAssociation().getName()));

    }

    void createAMatch() {
        List<TeamModel> listOfTeams = teamService.findAll();

        SeasonModel season = seasonService.save(new SeasonModel(
                LocalDate.now(),
                LocalDate.now().plusMonths(1),
                "Sesong 7",
                "den kjedeligste så langt"
        ));

        assertTrue(listOfTeams.size() >= 2);

        System.out.println("_____");
        AddressModel address = addressService.createAddress(new AddressModel("OSLO", "OSLO", "OSLO", "OSLO"));
        locationService.save(new LocationModel(address, "OSLO", "OSLO"));

        locationService.findAll().forEach(System.out::println);
        System.out.println(locationService.findAll().size());
        System.out.println("_____");
        assertTrue(locationService.findAll().size() >= 1);

        assertNotNull(locationService.findAll().get(0));

        MatchModel match = matchService.save(new MatchModel(
                LocalDate.now(),
                listOfTeams.get(0),
                listOfTeams.get(1),   //listOfTeams.get(1),
                season,      //season,
                locationService.findAll().get(0)      //locationService.findAll().get(0)
        ));

        //ikke en god test hvis vi begynner å fylle opp databasen med mathces
        assertEquals(match.getMatchId().intValue(), 1);

    }

    @Test
    void assignGoalToTeam() {
        createAMatch();

        //WOOHOOO en spiller scorte et poeng
        MatchModel match = matchService.findAll().get(0);
        PlayerModel player = playerService.findById(playerIdWithTeam).get();


        assertNotNull(player.getTeam());

        matchService.playerScorePoint(match, player, "hei");


        MatchGoalModel matchGoal = matchGoalService.findByPlayer(player).get(0);

        assertEquals(matchGoal.getGoalType(), GoalType.SCORPION_KICK);

        assertEquals(matchGoal.getMatch().getMatchId(), match.getMatchId());

        List<MatchGoalModel> matchGoals =  matchGoalService.findByMatch(match);

        assertFalse(matchGoals.isEmpty());

        System.out.println(match.getSeason().getName());

        matchService.getMatchStats(match);



    }



}