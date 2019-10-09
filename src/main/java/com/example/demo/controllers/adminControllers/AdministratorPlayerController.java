package com.example.demo.controllers.adminControllers;

import com.example.demo.models.PlayerModel;
import com.example.demo.repositories.PlayerRepository;
import com.example.demo.services.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController @RequestMapping("/v1/admin")
public class AdministratorPlayerController {
    @Autowired
    PlayerService playerService;

    @GetMapping("/get/player/{playerId}")
    public PlayerModel getPlayer(@PathVariable int playerId) {

        Optional<PlayerModel> playerModel = playerService.findById(playerId);
        if (playerModel.isPresent()) {
            return playerModel.get();
        }
        return null;
    }

    @PostMapping("/post/player")
    public PlayerModel addPlayer(@RequestBody PlayerModel playerModel) {
        PlayerModel newPlayer = playerService.save(playerModel);
        return newPlayer;
    }
}
