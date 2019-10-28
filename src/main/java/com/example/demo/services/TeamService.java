package com.example.demo.services;

import com.example.demo.dtos.TeamDTO;
import com.example.demo.exceptions.ElementNotFoundException;
import com.example.demo.models.*;
import com.example.demo.repositories.TeamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.xml.bind.Element;
import java.time.LocalDate;
import java.util.Collection;
import java.util.HashMap;
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

    @Autowired PlayerService playerService;

    @Autowired MatchService matchService;

    @Autowired MatchGoalService matchGoalService;

    @Autowired SeasonService seasonService;


    public Optional<TeamModel> findByAssociation(AssociationModel associationModel) {
        return teamRepository.findByAssociation(associationModel);
    }

    public List<TeamModel> findByCoach(CoachModel coach) {
        return teamRepository.findByCoach(coach);
    }

    public List<TeamModel> findByCoach(int coachId) {
        CoachModel coach = coachService.findById(coachId).orElseThrow(() -> new ElementNotFoundException("did not find this coach"));

        return teamRepository.findByCoach(coach);
    }

    public TeamModel save(TeamModel teamModel) {
        return teamRepository.save(teamModel);
    }

    private TeamModel convert(TeamDTO input) {
        Optional<AssociationModel> association = associationService.findById(input.getAssociationId());
        Optional<CoachModel> coach = coachService.findById(input.getCoachId());
        Optional<OwnerModel> owner = ownerService.findById(input.getOwnerId());
        Optional<LocationModel> location = locationService.findById(input.getLocationId());

        if ( !association.isPresent() || !coach.isPresent() || !owner.isPresent() || !location.isPresent() ) {
            throw new ElementNotFoundException("Could not locate one or several IDs in database");
        }

        return new TeamModel(association.get(), coach.get(), owner.get(), location.get());
    }

    public TeamModel create(TeamDTO input) throws ElementNotFoundException {

        TeamModel converted = convert(input);
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
    // that some fancy englihsh.

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


    public List<TeamModel> findTeamsWithNoOwner() {
        return teamRepository.findAll().stream().filter(team -> team.getOwner() == null).collect(Collectors.toList());
    }

    public TeamModel createTeam(AssociationModel associationModel, CoachModel coachModel, OwnerModel ownerModel, LocationModel locationModel) {
        TeamModel teamModel = new TeamModel(
            associationModel, coachModel, ownerModel, locationModel
        );

        return teamRepository.save(teamModel);
    }

    public HashMap<String, Object> getTeamStats(int teamId) {
        Long numberOfPlayers = 0L;
        int goalThisSeason = 0;
        Long totalGoals = 0L;
        PlayerModel playerWithMostGoals = null;
        String current_season = "";
        HashMap<String, Object> map = new HashMap<>();
        numberOfPlayers = playerService.findAll().stream().filter(player -> player.getTeam().getTeamId().equals(teamId)).collect(Collectors.counting());
        totalGoals = matchGoalService.findAll().stream().filter(goal -> goal.getPlayer().getTeam().getTeamId().equals(teamId)).collect(Collectors.counting());


        map.put("numberOfPlayers", numberOfPlayers);
        map.put("totalGoals", totalGoals);
        map.put("playerWithMostGoals", getPlayerWithMostGoalsGivenTeam(teamId));
        map.put("activeSeason", getCurrentSeason(teamId));
        return map;
    }

    private HashMap<String,Object> getPlayerWithMostGoalsGivenTeam(int teamId) {
        HashMap<String, Object > map = new HashMap<>();
        List<PlayerModel> playerList = playerService.findAll().stream().filter(player -> player.getTeam().getTeamId().equals(teamId)).collect(Collectors.toList());
        int goals = 0;
        PlayerModel playerRef = null;
        for(PlayerModel player : playerList) {
            int temp = matchGoalService.findByPlayer(player).size();
            if(temp > goals) {
                goals = temp;
                playerRef = player;
            }
        }
        map.put("player", playerRef);
        map.put("goals", goals);
        return map;
    }

    private SeasonModel getCurrentSeason(int teamId) {
        // All of these matches are within the same season which is currently active
        List<MatchModel> matches = matchService.findByTeam(teamId).stream().filter(match ->
                !(LocalDate.now().isBefore(match.getSeason().getStartDate()) ||
                  LocalDate.now().isAfter(match.getSeason().getEndDate()))).collect(Collectors.toList());
        return matches.get(0).getSeason();
    }
    // including inactive
    public List<TeamModel> findAllForced() {
        return teamRepository.findAll();
    }
}
