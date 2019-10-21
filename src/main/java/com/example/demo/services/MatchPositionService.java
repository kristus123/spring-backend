package com.example.demo.services;

import com.example.demo.dtos.MatchPositionDTO;
import com.example.demo.enums.GoalType;
import com.example.demo.exceptions.ElementNotFoundException;
import com.example.demo.models.MatchModel;
import com.example.demo.models.MatchPositionId;
import com.example.demo.models.MatchPositionModel;
import com.example.demo.models.PlayerModel;
import com.example.demo.repositories.MatchPositionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MatchPositionService {

    @Autowired
    MatchPositionRepository matchPositionRepository;

    @Autowired
    PlayerService playerService;

    @Autowired
    MatchService matchService;

    public MatchPositionModel save(MatchPositionModel matchPositionModel) {
        return matchPositionRepository.save(matchPositionModel);
    }

    private MatchPositionModel convert(MatchPositionDTO input) {
        Optional<PlayerModel> player = playerService.findById(input.getPlayerId());
        Optional<MatchModel> match = matchService.findById(input.getMatchId());

        if ( !player.isPresent() || !match.isPresent() )
            return null;

        return new MatchPositionModel(player.get(), match.get(), input.getPosition());
    }

    public MatchPositionModel create(MatchPositionDTO input) throws ElementNotFoundException {

        MatchPositionModel converted = convert(input);
        if (converted == null)
            throw new ElementNotFoundException("Could not locate one or several IDs in database");

        return save(converted);
    }

    public void delete(MatchPositionModel matchPositionModel) {
        matchPositionRepository.delete(matchPositionModel);
    }

    public Optional<MatchPositionModel> findById(MatchPositionId id) {
        return matchPositionRepository.findById(id);
    }

    public List<MatchPositionModel> findAll() {
        return matchPositionRepository.findAll();
    }

    public MatchPositionModel update(MatchPositionModel matchPosition, MatchPositionModel oldMatchPosition) {
        MatchPositionModel updatedMatchPosition = null;
        if(oldMatchPosition.getPlayer() == matchPosition.getPlayer() && oldMatchPosition.getMatch() == matchPosition.getMatch()) {
            updatedMatchPosition = save(matchPosition);
        }
        return updatedMatchPosition;
    }
}
