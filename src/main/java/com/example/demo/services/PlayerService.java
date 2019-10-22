package com.example.demo.services;

import com.example.demo.dtos.PlayerDTO;
import com.example.demo.models.MatchModel;
import com.example.demo.models.PersonModel;
import com.example.demo.models.PlayerModel;
import com.example.demo.models.TeamModel;
import com.example.demo.repositories.PlayerRepository;
import com.example.demo.repositories.TeamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PlayerService {

    @Autowired
    private PlayerRepository playerRepository;

    @Autowired
    private TeamService teamService;

    @Autowired
    private PersonService personService;

    public PlayerModel save(PlayerModel player) {
        return playerRepository.save(player);
    }

    public PlayerModel savePlayerDTO(PlayerDTO player) {
        PlayerModel playerModel = new PlayerModel(player);
        if(!teamService.findById(player.getTeamId()).isPresent())
            return null;
//        if(!personService.findById(player.getPersonId()).isPresent())
//            return null;
//        playerModel.setPerson(personService.findById(player.getPersonId()).get());
        playerModel.setTeam(teamService.findById(player.getTeamId()).get());
        return playerRepository.save(playerModel);
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

    public PlayerModel update(PlayerDTO player, PlayerModel oldPlayer) {

        PlayerModel updatedPlayer = null;
        if(oldPlayer.getPlayerId() == player.getPlayerId()){
            System.out.println("Do we even try to update?");
            updatedPlayer = savePlayerDTO(player);
        }
        return updatedPlayer;
    }

    public void delete(PlayerModel player) {
        playerRepository.delete(player);
    }

    public Optional<PlayerModel> findById(int id) {
        return playerRepository.findById(id);
    }

    public List<PlayerModel> findAll() {
        return playerRepository.findAll();
    }

    public String filteredPlayer(PlayerModel player) {
        return "The player : " + player.getPlayername() + " plays for " + player.getTeam().getAssociation().getName();


    }


}
