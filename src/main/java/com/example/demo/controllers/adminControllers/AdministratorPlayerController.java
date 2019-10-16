package com.example.demo.controllers.adminControllers;

import com.example.demo.dtos.PlayerDTO;
import com.example.demo.dtos.PlayerHistoryDTO;
import com.example.demo.dtos.PlayerTeamHistoryDTO;
import com.example.demo.models.PlayerHistoryModel;
import com.example.demo.models.PlayerModel;
import com.example.demo.repositories.audit.IPlayerHistoryRepository;
import com.example.demo.services.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController @RequestMapping("/v1/admin")
public class AdministratorPlayerController {
    @Autowired
    PlayerService playerService;


    private final IPlayerHistoryRepository playerHistoryRepository;

    @Autowired
    public AdministratorPlayerController(IPlayerHistoryRepository repo) {
        this.playerHistoryRepository = repo;
    }

    @GetMapping("/get/player/{playerId}")
    public PlayerModel getPlayer(@PathVariable int playerId) {

        Optional<PlayerModel> playerModel = playerService.findById(playerId);
        if (playerModel.isPresent()) {
            return playerModel.get();
        }
        return null;
    }

    @GetMapping("/get/player/{playerId}/history")
    public PlayerHistoryDTO getPlayerHistory(@PathVariable int playerId) {
        if(!playerService.findById(playerId).isPresent())
            return null;
        PlayerHistoryDTO playerHistoryDTO = new PlayerHistoryDTO();
        playerHistoryDTO.setPlayer(playerService.findById(playerId).get());
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

    @PostMapping("/post/player")
    public PlayerModel addPlayer(@RequestBody PlayerDTO playerModel) {
        PlayerModel newPlayer = playerService.savePlayerDTO(playerModel);
        return newPlayer;
    }

    @PutMapping("/update/player/{playerId}")
    public PlayerModel updatePlayer(@PathVariable int playerId, @RequestBody PlayerDTO playerModel) {
        Optional<PlayerModel> oldPlayer = playerService.findById(playerId);
        if(oldPlayer.isPresent()) {
            PlayerModel updatedPlayer = playerService.update(playerModel, oldPlayer.get());
            return updatedPlayer;
        }
        return null;
    }

    @DeleteMapping("/delete/player/{playerId}")
    public PlayerModel deletePlayer(@PathVariable int playerId) {
        Optional<PlayerModel> player = playerService.findById(playerId);
        if(player.isPresent()) {
            PlayerModel tempPlayer = player.get();
            playerService.delete(player.get());
            return tempPlayer;
        }

        return null;
    }




}
