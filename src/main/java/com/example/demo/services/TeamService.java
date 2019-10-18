package com.example.demo.services;

import com.example.demo.dtos.TeamDTO;
import com.example.demo.models.*;
import com.example.demo.repositories.TeamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TeamService {

    @Autowired TeamRepository teamRepository;

    @Autowired AssociationService associationService;

    @Autowired CoachService coachService;

    @Autowired OwnerService ownerService;

    @Autowired LocationService locationService;

    public TeamModel save(TeamModel teamModel) {
        return teamRepository.save(teamModel);
    }

    private TeamModel convert(TeamDTO input) {
        Optional<AssociationModel> association = associationService.findById(input.getAssociationId());
        Optional<CoachModel> coach = coachService.findById(input.getCoachId());
        Optional<OwnerModel> owner = ownerService.findById(input.getOwnerId());
        Optional<LocationModel> location = locationService.findById(input.getLocationId());

        if ( !association.isPresent() || !coach.isPresent() || !owner.isPresent() || !location.isPresent() ) {
            return null;
        }

        return new TeamModel(association.get(), coach.get(), owner.get(), location.get());
    }

    public TeamModel save(TeamDTO input) {

        Optional<TeamModel> returned = teamRepository.findById(input.getTeamId());
        if (returned.isPresent())
            return null;

        TeamModel converted = convert(input);
        if (converted == null)
            return null;

        return teamRepository.save(converted);
    }

    public TeamModel update(TeamModel team, TeamModel oldTeam) {

        TeamModel updatedTeam = null;
        if (oldTeam.getTeamId() == team.getTeamId()) {
            updatedTeam = save(team);
        }

        return updatedTeam;
    }

    public TeamModel update(TeamDTO input, TeamModel oldTeam) {

        if (oldTeam.getTeamId() != input.getTeamId() || !oldTeam.isActive())
            return null;

        TeamModel updatedTeam = convert(input);
        updatedTeam.setTeamId(input.getTeamId());
        return teamRepository.save(updatedTeam);
    }

    /*
        We would like to keep our teams in DB for the sake of information in matches.
        Hence only updating a variable regarding the team's active-status
     */
    public void delete(TeamModel team) {
        team.setActive(false);
        teamRepository.save(team);
    }

    public void deleteById(Integer id) {
        Optional<TeamModel> team = findById(id);
        if (!team.isPresent())
            return;
        team.get().setActive(false);
        teamRepository.save(team.get());
    }

    public Optional<TeamModel> findById(Integer id) {
        Optional<TeamModel> team = teamRepository.findById(id);
        if (!team.isPresent() || !team.get().isActive())
            return Optional.empty();
        return team;
    }

    public List<TeamModel> findAllActive() {
        return teamRepository.findAll().stream().filter(team -> team.isActive()).collect(Collectors.toList());
    }

    public TeamModel createTeam(AssociationModel associationModel, CoachModel coachModel, OwnerModel ownerModel, LocationModel locationModel) {
        TeamModel teamModel = new TeamModel(
            associationModel, coachModel, ownerModel, locationModel
        );

        return teamRepository.save(teamModel);
    }

    public List<TeamModel> findAllIncludingInactive() {
        return teamRepository.findAll();
    }
}
