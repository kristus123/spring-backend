package com.example.demo.services;

import com.example.demo.dtos.PlayerAnonDTO;
import com.example.demo.dtos.PlayerDTO;
import com.example.demo.exceptions.ElementNotFoundException;
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
    PlayerRepository playerRepository;

    @Autowired
    TeamService teamService;

    @Autowired
    PersonService personService;


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

    public PlayerModel update(Integer id, PlayerDTO player) throws ElementNotFoundException {
        findById(id).orElseThrow(() -> new ElementNotFoundException("Could not find team with ID=" + id));

        PlayerModel updatedPlayer = convert(player);
        updatedPlayer.setPlayerId(id);
        return save(updatedPlayer);
    }

    public PlayerModel deleteById(Integer id) throws ElementNotFoundException {
        PlayerModel player = findById(id).orElseThrow(() -> new ElementNotFoundException("Could not find player with ID=" + id));
        playerRepository.deleteById(id);
        return player;
    }

    public Optional<PlayerModel> findById(int id) {
        return playerRepository.findById(id);
    }

    public List<PlayerModel> findAll() {
        return playerRepository.findAll();
    }

    public PlayerAnonDTO filteredPlayer(PlayerModel player) {
        return new PlayerAnonDTO(player, player.getPlayername(), player.getTeam().toString());

    }

}
