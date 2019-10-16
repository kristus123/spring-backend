package com.example.demo.services;

import com.example.demo.models.*;
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

    public TeamModel update(TeamModel team, TeamModel oldTeam) {

        TeamModel updatedTeam = null;
        if (oldTeam.getTeamId() == team.getTeamId()) {
            updatedTeam = save(team);
        }

        return updatedTeam;
    }

    /*
        We would like to keep our teams in DB for the sake of information in matches.
        Hence only updating a variable regarding the team's active-status
     */
    public void delete(TeamModel team) {
        team.setActive(false);
        teamRepository.save(team);
        //teamRepository.delete(team);
    }

    public void deleteById(Integer id) {
        Optional<TeamModel> team = findById(id);
        if (!team.isPresent())
            return;
        team.get().setActive(false);
        teamRepository.save(team.get());
        //teamRepository.deleteById(id);
    }

    public Optional<TeamModel> findById(Integer id) {
        return teamRepository.findById(id);
    }

    public List<TeamModel> findAll() {
        return teamRepository.findAll();
    }

    public TeamModel createTeam(AssociationModel associationModel, CoachModel coachModel, OwnerModel ownerModel, LocationModel locationModel) {
        TeamModel teamModel = new TeamModel(
            associationModel, coachModel, ownerModel, locationModel
        );

        return teamRepository.save(teamModel);
    }
}
