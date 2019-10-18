package com.example.demo.services;

import com.example.demo.dtos.TeamDTO;
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

    @Autowired CoachService coachService;
    @Autowired LocationService locationService;
    @Autowired AssociationService associationService;
    @Autowired OwnerService ownerService;

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

    public TeamModel createTeam(AssociationModel associationModel, CoachModel coachModel, OwnerModel ownerModel, LocationModel locationModel) {
        TeamModel teamModel = new TeamModel(
            associationModel, coachModel, ownerModel, locationModel
        );

        return teamRepository.save(teamModel);
    }



    public TeamModel createTeam(TeamDTO teamDTO) {
        Optional<CoachModel> coach =  coachService.findById(teamDTO.getCoachId());
        Optional<AssociationModel> association = associationService.findById(teamDTO.getAssociationId());
        Optional<OwnerModel> owner = ownerService.findById(teamDTO.getOwnerId());
        Optional<LocationModel> location = locationService.findById(teamDTO.getLocationId());

        TeamModel teamModel = new TeamModel(); {
            if (association.isPresent()) teamModel.setAssociation(association.get());
            if (coach.isPresent()) teamModel.setCoach(coach.get());
            if (owner.isPresent()) teamModel.setOwner(owner.get());
            if (location.isPresent()) teamModel.setLocation(location.get());
        }

        return teamRepository.save(teamModel);
    }









}
