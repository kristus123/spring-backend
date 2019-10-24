package com.example.demo.services;

import com.example.demo.dtos.*;
import com.example.demo.exceptions.ElementNotFoundException;
import com.example.demo.models.*;
import com.example.demo.repositories.PlayerRepository;
import com.example.demo.repositories.audit.IPlayerHistoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PlayerService {

    @Autowired
    PlayerRepository playerRepository;

    @Autowired
    TeamService teamService;

    @Autowired
    PersonService personService;

    @Autowired
    MatchGoalService matchGoalService;

    @Autowired
    MatchService matchService;

    @Autowired
    SeasonService seasonService;

    @Autowired
    private IPlayerHistoryRepository playerHistoryRepository;


    private PlayerModel convert(PlayerDTO input) {
        Optional<PersonModel> person = personService.findById(input.getPersonId());
        Optional<TeamModel> team = teamService.findById(input.getTeamId());

        if ( !person.isPresent() || !team.isPresent() )
            throw new ElementNotFoundException("Could not locate one or several IDs in database");

        return new PlayerModel(
                person.get(),
                team.get(),
                input.getNormalPosition(),
                input.getPlayerNumber(),
                input.getTeamDateFrom(),
                input.getTeamDateTo(),
                input.getPlayername());
    }

    public PlayerModel save(PlayerModel player) {
        return playerRepository.save(player);
    }

    public PlayerModel create(PlayerDTO input) {
        PlayerModel converted = convert(input);
        return save(converted);
    }

    public PlayerModel turnIntoPlayer(PersonModel person) {
        Optional<PlayerModel> player = playerRepository.findByPerson(person);
        if (player.isPresent()) {
            System.out.println("already a player");
            return player.get();
        }
        return playerRepository.save(new PlayerModel(person));
    }



    public List<PlayerModel> getAllPlayersOfTeam(TeamModel teamModel) {
        return playerRepository.findByTeam(teamModel);

    }
    public List<PlayerModel> getAllPlayersOfTeam(int teamId) {
        Optional<TeamModel> team =  teamService.findById(teamId);
        if (team.isPresent()) return getAllPlayersOfTeam(team.get());

        throw new RuntimeException("player Id Not found");
    }

    public PlayerModel update(Integer id, PlayerDTO player) throws ElementNotFoundException {
        findById(id).orElseThrow(() -> new ElementNotFoundException("Could not find team with ID=" + id));

        PlayerModel updatedPlayer = convert(player);
        updatedPlayer.setPlayerId(id);
        return save(updatedPlayer);
    }

    public PlayerModel deleteById(Integer id) throws ElementNotFoundException {
        PlayerModel player = findById(id).orElseThrow(() -> new ElementNotFoundException("Could not find player with ID=" + id));
        player.setActive(false);
        return playerRepository.save(player);
    }

    public Optional<PlayerModel> findById(int id) {
        Optional<PlayerModel> player = playerRepository.findById(id);
        if (!player.isPresent() || !player.get().isActive())
            return Optional.empty();
        return player;
    }

    public List<PlayerModel> findAllActive() {
        return playerRepository.findAll().stream().filter(player -> player.isActive()).collect(Collectors.toList());
    }

    public Optional<PlayerModel> findByIdForced(Integer id) {
        return playerRepository.findById(id);
    }

    public List<PlayerModel> findAllForced() {
        return playerRepository.findAll();
    }

    public Optional<PlayerModel> findByPerson(PersonModel personModel) {
        return playerRepository.findByPerson(personModel);
    }
    public PlayerAnonDTO filteredPlayer(Optional<PlayerModel> player) {
        return new PlayerAnonDTO(player.get(), player.get().getPlayername(), player.get().getTeam().toString());

    }


    /*
    * This method calculates and returns an object containing:
    * Goals for the newest season
    * Total goals scored
    * Average goals per game
    * Average goal for all player per game
    *
    * */
    public PlayerStatsDTO getPlayerStats(Integer playerId) {
        PlayerStatsDTO playerStatsDTO = new PlayerStatsDTO();
        Optional<PlayerModel> player = playerRepository.findById(playerId);
        if(!player.isPresent()) {
            return null;
        }
        // Get total goals
        Integer totGoals = matchGoalService.findByPlayerId(playerId).size();
        Integer seasonGoal;

        /*
        * Get all teams the player has been in and find all games which has been played when he
        * has been in the team
        * */

        Double avgGoals = (double)totGoals / (double)getAverageGoalsPerGame(playerId);
        playerStatsDTO.
                setAverageGoal(avgGoals).
                setTotalGoals(totGoals).
                setSeasonGoals(getCurrentSeasonGoals(playerId)).
                setGoalTypes(getGoals(playerId));
        return playerStatsDTO;
    }

    private Integer getAverageGoalsPerGame(int playerId) {
        List<MatchModel> playerMatches = new ArrayList<>();
        // All teams player has been in
        for(PlayerTeamHistoryDTO team : getPlayerHistory(playerId).getPlayerTeamHistory()) {
            // All matches the team has played while the player was present (FP solution???)
            System.out.println(matchService.findByTeam(team.getTeamId()).get(0).getMatchDate());
            System.out.println(team.getTeamDateFrom());
            System.out.println(team.getTeamDateTo());
            playerMatches = matchService.findByTeam(team.getTeamId()).stream().filter(match -> !(match.getMatchDate().
                    isBefore(team.getTeamDateFrom() )  ||
                    match.getMatchDate().isAfter(team.getTeamDateTo()) )).collect(Collectors.toList());
        }
        return playerMatches.size();
    }

    private HashMap<String, Integer> getGoals(int playerId) {
        List<MatchGoalModel> playerGoals = matchGoalService.findByPlayerId(playerId);
        HashMap<String, Integer> goalTypes = new HashMap<>();
        // Get all goal types
        for(MatchGoalModel goal : playerGoals) {
            goalTypes.put(goal.getGoalType().getTypeName(), 0);
        }
        // Increment number of each goal type
        for(MatchGoalModel goal : playerGoals) {
            goalTypes.put(goal.getGoalType().getTypeName(), goalTypes.get(goal.getGoalType().getTypeName()) + 1);
        }
        return goalTypes;
    }

    private Integer getCurrentSeasonGoals(int playerId) {
        // Check if current date is within a season
        // Check if match has player with playerId
        // PROFIT!?!?!
        Integer seasonGoals = 0;
        Boolean foundSeason = false;
        for(SeasonModel season : seasonService.findAll()) {

            // We have found a season which is currently active
            if( !(LocalDate.now().isBefore(season.getStartDate()) || LocalDate.now().isAfter(season.getEndDate())) )
            {
                seasonGoals = 0;

                /*
                * Oh no!! The player has been participated in multiple season that are currently active
                * Just take the latest found for now
                * Should find the most recent*/

                // Get all within that season
                List<MatchModel> matches = matchService.findAll().stream().filter(match -> match.getSeason().getSeasonId().equals(season.getSeasonId())).collect(Collectors.toList());
                /*Now we need to check if a player has been participating in these matches,
                and count how many goals he scored*/
                for(MatchModel match : matches) {
                    if(match.getHomeTeam().getTeamId().equals(findById(playerId).get().getTeam().getTeamId()) || match.getAwayTeam().getTeamId().equals(findById(playerId).get().getTeam().getTeamId())) {
                        seasonGoals += (int)matchGoalService.findByMatch(match).stream().filter(goal -> goal.getPlayer().getPlayerId() == playerId).count();
                    }
                }
            }
        }
        return seasonGoals;
    }

    public PlayerHistoryDTO getPlayerHistory(int playerId) {
        if(!findById(playerId).isPresent())
            return null;
        PlayerHistoryDTO playerHistoryDTO = new PlayerHistoryDTO();
        playerHistoryDTO.setPlayer(findById(playerId).get());
        List<PlayerHistoryModel> list = playerHistoryRepository.listPlayerHistoryRevisions(playerId);
        Integer tempTeamId = -1;
        for(PlayerHistoryModel player_hist : list) {
            if(player_hist.getPlayerModel().getTeam().getTeamId() != tempTeamId) {
                playerHistoryDTO.getPlayerTeamHistory().add(new PlayerTeamHistoryDTO(
                        player_hist.getPlayerModel().getTeamDateFrom(),
                        player_hist.getPlayerModel().getTeamDateTo(),
                        player_hist.getPlayerModel().getTeam().getTeamId()
                ));
                tempTeamId = player_hist.getPlayerModel().getTeam().getTeamId();
            }
        }
        return playerHistoryDTO;
    }

    public List<PlayerModel> findAll() {
        return playerRepository.findAll();
    }

}
