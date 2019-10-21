package com.example.demo.services;

import com.example.demo.dtos.MatchResultDTO;
import com.example.demo.enums.GoalType;
import com.example.demo.models.*;
import com.example.demo.repositories.MatchRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class MatchService {

    @Autowired
    private MatchRepository matchRepository;

    @Autowired
    MatchGoalService matchGoalService;

    public MatchModel save(MatchModel match) {
        return matchRepository.save(match);
    }

    public void deleteById(Integer id) {
        matchRepository.deleteById(id);
    }

    public Optional<MatchModel> findById(Integer id) {
        return matchRepository.findById(id);
    }

    public List<MatchModel> findAll() {
        return matchRepository.findAll();
    }

    public MatchGoalModel playerScorePoint(MatchModel match, PlayerModel player, String description) {
        return matchGoalService.save(new MatchGoalModel(
                player, //player,
                GoalType.SCORPION_KICK,
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
            System.out.println(g.getPlayer().getPerson().getFirstName() + " scorte en " + g.getGoalType() + " og han spiller for " + g.getPlayer().getTeam());
        });

        System.out.println("___");

        System.out.println("home : " + home + " ( home er " + match.getHomeTeam() + " )");
        System.out.println("away : " + away + " ( home er " + match.getAwayTeam() + " )");

        if (home > away) {
            System.out.println(match.getHomeTeam().getAssociation().getName() + " er vinneren");
        } else if (home == away) System.out.println("det ble uavgjort");
        else System.out.println(match.getAwayTeam().getAssociation().getName() + " vant");


    }


    public MatchResultDTO getFilteredMatchStats(MatchModel match) {
        String result;
        List<MatchGoalModel> goals = matchGoalService.findByMatch(match);
        long home = 0;
        long away = 0;
        home = goals.stream()
                .filter(g -> match.getHomeTeam()
                        .getAssociation()
                        .getName().equals(g.getPlayer().getTeam().getAssociation().getName())).count();

        away = goals.size() - home;

        if (home > away) {
            result = match.getHomeTeam().getAssociation().getName().toString();
        } else if (home == away) result = "Uavgjort";
        else {
            result = match.getAwayTeam().getAssociation().getName().toString();
        }
        return new MatchResultDTO(match, match.getHomeTeam(), match.getAwayTeam(), result);
    }
}
