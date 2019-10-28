package com.example.demo.services;

import com.example.demo.dtos.MatchDTO;
import com.example.demo.dtos.MatchResultDTO;
import com.example.demo.enums.GoalType;
import com.example.demo.exceptions.ElementNotFoundException;
import com.example.demo.models.*;
import com.example.demo.repositories.MatchRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class MatchService {

    @Autowired
    MatchRepository matchRepository;

    @Autowired
    MatchGoalService matchGoalService;

    @Autowired
    TeamService teamService;

    @Autowired
    SeasonService seasonService;

    @Autowired
    LocationService locationService;

    @Autowired GoalTypeService goalTypeService;


    private MatchModel convert(MatchDTO input) {
        Optional<TeamModel> homeTeam = teamService.findById(input.getHomeTeamId());
        Optional<TeamModel> awayTeam = teamService.findById(input.getAwayTeamId());
        Optional<SeasonModel> season = seasonService.findById(input.getSeasonId());
        Optional<LocationModel> location = locationService.findById(input.getLocationId());

        if ( !homeTeam.isPresent() || !awayTeam.isPresent() || !season.isPresent() || !location.isPresent() )
            throw new ElementNotFoundException("Could not locate one or several IDs in database");

        return new MatchModel(
                input.getMatchDate(),
                homeTeam.get(),
                awayTeam.get(),
                season.get(),
                location.get()
        );
    }

    public MatchModel save(MatchModel match) {
        return matchRepository.save(match);
    }

    public MatchModel create(MatchDTO input) throws ElementNotFoundException {
        MatchModel converted = convert(input);
        return save(converted);
    }

    public MatchModel update(Integer id, MatchDTO input) {

        findById(id).orElseThrow(() -> new ElementNotFoundException("Could not find match with ID=" + id));

        MatchModel updatedMatch = convert(input);
        updatedMatch.setMatchId(id);
        return save(updatedMatch);
    }

    public void delete(MatchModel match) {
        matchRepository.delete(match);
    }

    public MatchModel deleteById(Integer id) {
        MatchModel match = findById(id)
                .orElseThrow(() -> new ElementNotFoundException("Could not find match with ID=" + id));
        matchRepository.deleteById(id);
        return match;
    }

    public Optional<MatchModel> findById(Integer id) {
        return matchRepository.findById(id);
    }

    public List<MatchModel> findAll() {
        return matchRepository.findAll();
    }

    public List<MatchModel> findByTeam(Integer teamId) {
        Optional<TeamModel> team = teamService.findById(teamId);
        if(!team.isPresent()) {
            return null;
        }

        return findAll().stream().filter(match -> match.getHomeTeam().getTeamId().equals(teamId) ||
                match.getAwayTeam().getTeamId().equals(teamId)).collect(Collectors.toList());
    }

    public MatchGoalModel playerScorePoint(MatchModel match, PlayerModel player, String description) {
        return matchGoalService.save(new MatchGoalModel(
                player, //player,
                goalTypeService.save(new GoalTypeModel("SCORPION_KICK")),
                match,
                description
        ));
    }


    public void getMatchStats(MatchModel match) {
        //Get all goals scored in the match:
        List<MatchGoalModel> goals = matchGoalService.findByMatch(match);
        long home = 0;
        long away = 0;
        System.out.println("___");

        home = goals.stream()
                .filter(g -> match.getHomeTeam()
                        .getAssociation()
                        .getName().equals(g.getPlayer().getTeam().getAssociation().getName())).count();

        away = goals.size() - home;


        goals.forEach(g -> {
            System.out.println(g.getPlayer().getPerson().getFirstName() + " scorte en " + g.getGoalType().getTypeName() + " og han spiller for " + g.getPlayer().getTeam());
        });

        System.out.println("___");

        System.out.println("home : " + home + " ( home er " + match.getHomeTeam() + " )");
        System.out.println("away : " + away + " ( home er " + match.getAwayTeam() + " )");

        if (home > away) {
            System.out.println(match.getHomeTeam().getAssociation().getName() + " er vinneren");
        } else if (home == away) System.out.println("det ble uavgjort");
        else System.out.println(match.getAwayTeam().getAssociation().getName() + " vant");


    }

    public HashMap<String,Object> getFilteredMatchStats(MatchModel match, int teamId) {
        HashMap<String, Object> map = new HashMap<>();
        String result = "";
        List<MatchGoalModel> goals = matchGoalService.findByMatch(match);
        long home = 0;
        long away = 0;
        home = goals.stream()
                .filter(g -> match.getHomeTeam()
                        .getAssociation()
                        .getName().equals(g.getPlayer().getTeam().getAssociation().getName())).count();

        away = goals.size() - home;
        if(match.getHomeTeam().getTeamId().equals(teamId)) {
            if (home > away)
                result = "win";
            else if (home < away)
                result = "lost";
            else
                result = "draw";
        }
        else {
            if (home < away)
                result = "win";
            else if (home > away)
                result = "lost";
            else
                result = "draw";
        }
        map.put("home", home);
        map.put("away", away);
        map.put("result", result);
        map.put("homeTeam", match.getHomeTeam().getAssociation().getName());
        map.put("awayTeam", match.getAwayTeam().getAssociation().getName());
        return map;
    }

    public MatchResultDTO getFilteredMatchStats(Optional <MatchModel> match) {
        String result;
        List<MatchGoalModel> goals = matchGoalService.findByMatch(match.get());
        long home = 0;
        long away = 0;
        home = goals.stream()
                .filter(g -> match.get().getHomeTeam()
                        .getAssociation()
                        .getName().equals(g.getPlayer().getTeam().getAssociation().getName())).count();

        away = goals.size() - home;

        if (home > away) {
            result = match.get().getHomeTeam().getAssociation().getName().toString();
        } else if (home == away) result = "Draw";
        else {
            result = match.get().getAwayTeam().getAssociation().getName().toString();
        }
        return new MatchResultDTO(match.get(), match.get().getHomeTeam(), match.get().getAwayTeam(), result);
    }


}
