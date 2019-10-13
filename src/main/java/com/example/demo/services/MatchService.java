package com.example.demo.services;

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

    @Autowired private MatchRepository matchRepository;

    @Autowired MatchGoalService matchGoalService;

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


    public  void getMatchStats(MatchModel match) {
        //Get alle goals som er blitt scort
        List<MatchGoalModel> goals = matchGoalService.findByMatch(match);

        System.out.println("___");
        goals.forEach(g -> {
            System.out.println(g.getPlayer().getPerson().getFirstName() + " scorte en " + g.getGoalType() + " og han spiller for ");
            System.out.println(g.getPlayer().getTeam());
        });
        System.out.println("___");

        System.out.println(goals.get(0).getPlayer().getTeam());

    }






}
