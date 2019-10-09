package com.example.demo.services;

import com.example.demo.models.MatchModel;
import com.example.demo.repositories.MatchRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MatchService {

    @Autowired
    private MatchRepository matchRepository;

    public MatchModel save(MatchModel match) {
        return matchRepository.save(match);
    }

    public void delete(MatchModel match) {
        matchRepository.delete(match);
    }

    public Optional<MatchModel> findById(Integer id) {
        return matchRepository.findById(id);
    }

    public List<MatchModel> findAll() {
        return matchRepository.findAll();
    }


}
