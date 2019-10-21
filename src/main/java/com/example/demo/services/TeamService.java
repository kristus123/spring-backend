package com.example.demo.services;

import com.example.demo.dtos.TeamDTO;
import com.example.demo.exceptions.ElementNotFoundException;
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

    public TeamModel create(TeamDTO input) throws ElementNotFoundException {

        TeamModel converted = convert(input);
        if (converted == null)
            throw new ElementNotFoundException("Could not locate one or several IDs in database");

        return save(converted);
    }

    public TeamModel update(Integer id, TeamDTO input) throws ElementNotFoundException {

        findById(id).orElseThrow(() -> new ElementNotFoundException("Could not find team with ID=" + id));

        TeamModel updatedTeam = convert(input);
        updatedTeam.setTeamId(id);
        return save(updatedTeam);
    }

    /*
        We would like to keep our teams in DB for the sake of information in matches.
        Hence only updating a variable regarding the team's active-status
     */

    public TeamModel deleteById(Integer id) throws ElementNotFoundException {
        TeamModel team = findById(id)
                .orElseThrow(() -> new ElementNotFoundException("Could not find team with ID=" + id));
        team.setActive(false);
        return teamRepository.save(team);
    }

    public Optional<TeamModel> findById(Integer id) {
        Optional<TeamModel> team = teamRepository.findById(id);
        if (!team.isPresent() || !team.get().isActive())
            return Optional.empty();
        return team;
    }

    // including inactive
    public Optional<TeamModel> findByIdForced(Integer id) {
        return teamRepository.findById(id);
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

    // including inactive
    public List<TeamModel> findAllForced() {
        return teamRepository.findAll();
    }
}
