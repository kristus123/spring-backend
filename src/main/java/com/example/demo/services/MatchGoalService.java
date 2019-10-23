package com.example.demo.services;

import com.example.demo.dtos.MatchGoalDTO;
import com.example.demo.exceptions.ElementBadRequestException;
import com.example.demo.exceptions.ElementNotFoundException;
import com.example.demo.models.MatchGoalModel;
import com.example.demo.models.MatchModel;
import com.example.demo.models.PlayerModel;
import com.example.demo.repositories.MatchGoalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MatchGoalService {

    @Autowired
    MatchGoalRepository matchGoalRepository;

    @Autowired
    PlayerService playerService;

    @Autowired
    MatchService matchService;

    private MatchGoalModel convert(MatchGoalDTO input) {
        Optional<PlayerModel> player = playerService.findById(input.getPlayerId());
        Optional<MatchModel> match = matchService.findById(input.getMatchId());

        if ( !player.isPresent() || !match.isPresent() )
            throw new ElementNotFoundException("Could not locate one or several IDs in database");

        return new MatchGoalModel(
                player.get(),
                input.getGoalType(),
                match.get(),
                input.getDescription()
        );
    }

    public MatchGoalModel save(MatchGoalModel matchGoalModel) {return matchGoalRepository.save(matchGoalModel);}

    public MatchGoalModel create(MatchGoalDTO input) throws ElementNotFoundException {
        MatchGoalModel converted = convert(input);
        return save(converted);
    }

    public MatchGoalModel update(Integer id, MatchGoalDTO input) throws ElementNotFoundException {

        findById(id).orElseThrow(() -> new ElementNotFoundException("Could not find match goal with ID=" + id));

        MatchGoalModel updatedMatchGoal = convert(input);
        updatedMatchGoal.setGoalId(id);
        return save(updatedMatchGoal);
    }
    public MatchGoalModel deleteById(Integer id) throws ElementNotFoundException {
        MatchGoalModel matchGoal = findById(id)
                .orElseThrow(() -> new ElementNotFoundException("Could not find match goal with ID=" + id));
        matchGoalRepository.deleteById(id);
        return matchGoal;
    }

    public Optional<MatchGoalModel> findById(Integer id) {return matchGoalRepository.findById(id);}
    public List<MatchGoalModel> findAll() {return matchGoalRepository.findAll();}

    public List<MatchGoalModel> findByPlayer(PlayerModel player) {
        return matchGoalRepository.findByPlayer(player);
    }

    public List<MatchGoalModel> findByMatch(MatchModel match) {
        return matchGoalRepository.findByMatch(match);
    }

}
