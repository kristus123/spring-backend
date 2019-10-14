package com.example.demo.services;

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
    private MatchGoalRepository matchGoalRepository;

    public MatchGoalModel save(MatchGoalModel matchGoalModel) {return matchGoalRepository.save(matchGoalModel);}
    public MatchGoalModel update(Integer id, MatchGoalModel personModel) {
        if(!findById(id).isPresent())
            return null;
        personModel.setGoalId(id);
        return save(personModel);

    }
    public void delete(Integer id) {matchGoalRepository.deleteById(id);}
    public Optional<MatchGoalModel> findById(Integer id) {return matchGoalRepository.findById(id);}
    public List<MatchGoalModel> findAll() {return matchGoalRepository.findAll();}

    public List<MatchGoalModel> findByPlayer(PlayerModel player) {
        return matchGoalRepository.findByPlayer(player);
    }

    public List<MatchGoalModel> findByMatch(MatchModel match) {
        return matchGoalRepository.findByMatch(match);
    }

}
