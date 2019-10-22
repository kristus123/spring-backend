package com.example.demo.services;

import com.example.demo.enums.GoalType;
import com.example.demo.models.*;
import com.example.demo.repositories.*;
import org.hibernate.event.spi.SaveOrUpdateEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Date;
import java.time.LocalDate;
import java.time.Month;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Service
public class DummyDataService {
    //create everything necessary to have a complete match

    @Autowired TeamService teamService;

    @Autowired MatchService matchService;

    @Autowired MatchGoalService matchGoalService;

    @Autowired SeasonService seasonService;

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

    // Creates a team with stadium and given players that are attached to the team
    public TeamModel createTeam(String teamName, String stadiumName, String filePathToPlayerNames) {
        /* -------------- CREATE COACH -------------- */
        LocalDate date              = LocalDate.of(2015, 2, 2);
        AddressModel addressCoach   = addressService.createAddress(new AddressModel("489489", "OSLO", "SWEDEN", "ve30"));
        PersonModel personCoach     =  personService.create(new PersonModel("Alex", "Johansen", date, addressCoach));
        CoachModel coach            = new CoachModel(personCoach);
        coachService.save(coach);

        /* -------------- CREATE OWNER -------------- */
        AddressModel addressOwner   = addressService.createAddress(new AddressModel("323", "COPENHAGEN", "DENMARK", "road69"));
        PersonModel personOwner     =  personService.create(new PersonModel("Dun", "Butthole", date, addressOwner));
        OwnerModel owner            = new OwnerModel(personOwner);
        ownerService.save(owner);

        /* -------------- CREATE LOCATION -------------- */
        AddressModel addressLocation    = addressService.createAddress(new AddressModel("323", "BARCELONA", "SPAIN", "noob street 07"));
        LocationModel locationModel     = new LocationModel(addressLocation, stadiumName, "Fantastic stadium");
        locationService.save(locationModel);

        /* -------------- CREATE ASSOCIATION -------------- */
        AssociationModel associationModel = new AssociationModel(teamName, "Awesome club");
        associationService.save(associationModel);

        /* -------------- CREATE TEAM -------------- */
        //TeamModel teamModel = new TeamModel(associationModel, coach, owner, locationModel);
        System.out.println(owner.getPerson().getPersonId());
        TeamModel team = teamService.createTeam(associationModel, coach, owner, locationModel);
        teamService.save(team);
        //teamService.createTeam(associationModel, coach, owner, locationModel);

        /* -------------- CREATE PLAYERS -------------- */
        try {
            Path filePath = Paths.get("db_data/" + filePathToPlayerNames);
            BufferedReader br = new BufferedReader(new FileReader(filePath.toString()));
            String st;
            LocalDate playerDate = LocalDate.of(1985, 2, 2);
            AddressModel playerAddress = addressService.createAddress(new AddressModel("489489", "Barcelona", "Spain", "ve30"));
            while( (st = br.readLine()) != null) {
                String [] sp = st.split(" ");
                PersonModel personPlayer =  personService.create(new PersonModel(sp[0], sp[1], playerDate, playerAddress));
                PlayerModel playerModel = new PlayerModel(personPlayer, team, sp[2], sp[3], sp[0] + " " + sp[1], sp[4]);
                playerModel.setTeamDateFrom(LocalDate.of(2010, 01, 01));
                playerModel.setTeamDateTo(LocalDate.now());
                playerService.save(playerModel);
            }
        } catch(Exception ex) {
            System.err.println(ex.getMessage());
        }
        return team;
    }

    /*
     * <p>
     * Note: The goals are assigned to random players across both teams.
     *      The location will be the stadium of the home team
     * </p>
    * @Param season The season which the matches was played
    * @Param team1 Home team
    * @Param team2 Away team
    * @Param goals A text file with goals
    *
    * */
    public void createMatch(SeasonModel season, TeamModel homeTeam, TeamModel awayTeam, String filePathToGoals) {
        seasonService.save(season);
        MatchModel match = new MatchModel(LocalDate.now(), homeTeam, awayTeam, season, homeTeam.getLocation());
        matchService.save(match);
        getRandomPlayer(homeTeam, awayTeam);
        try {
            Path filePath = Paths.get("db_data/" + filePathToGoals);
            BufferedReader br = new BufferedReader(new FileReader(filePath.toString()));
            String st;
            while( (st = br.readLine()) != null) {
                String[] sp = st.split(" ");
                MatchGoalModel matchGoalModel = new MatchGoalModel(getRandomPlayer(homeTeam, awayTeam),
                       GoalType.valueOf(sp[1]),
                        match,
                        sp[0].replaceAll("[-]", " "));
                matchGoalService.save(matchGoalModel);
            }
        } catch (Exception ex) {
            System.err.println(ex.getMessage());
        }

    }

    /*
    * Get a random player from two teams
    * */
    private PlayerModel getRandomPlayer(TeamModel homeTeam, TeamModel awayTeam) {

        List<PlayerModel> allPlayers =  playerService.findAll().stream()
                .filter(player ->
                        player.getTeam().getTeamId() == awayTeam.getTeamId() ||
                        player.getTeam().getTeamId() == homeTeam.getTeamId())
                .collect(Collectors.toList());

        Random random = new Random();
        Integer r = random.nextInt(allPlayers.size() - 1 - 0 + 1) + 0;

        return allPlayers.get(r);
    }

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


