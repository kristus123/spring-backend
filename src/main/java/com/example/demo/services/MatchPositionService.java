package com.example.demo.services;

import com.example.demo.models.MatchPositionId;
import com.example.demo.models.MatchPositionModel;
import com.example.demo.repositories.MatchPositionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MatchPositionService {

    @Autowired
    private MatchPositionRepository matchPositionRepository;

    public MatchPositionModel save(MatchPositionModel matchPositionModel) {
        return matchPositionRepository.save(matchPositionModel);
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
