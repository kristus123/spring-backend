package com.example.demo.services;

import com.example.demo.models.TeamModel;
import com.example.demo.repositories.TeamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TeamService {

    @Autowired
    private TeamRepository teamRepository;

    public TeamModel save(TeamModel team) {
        return teamRepository.save(team);
    }

    public void deleteById(Integer id) {
        teamRepository.deleteById(id);
    }

    public Optional<TeamModel> findById(Integer id) {
        return teamRepository.findById(id);
    }

    public List<TeamModel> findAll() {
        return teamRepository.findAll();
    }
}
