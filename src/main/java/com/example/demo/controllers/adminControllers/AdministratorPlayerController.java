package com.example.demo.controllers.adminControllers;

import com.example.demo.models.PlayerModel;
import com.example.demo.services.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/v1/admin")
public class AdministratorPlayerController {

    @Autowired
    private PlayerService playerService;



    @PostMapping("/post/player")
    public PlayerModel newPlayer(@RequestBody PlayerModel playerModel) {
        return playerService.save(playerModel);
    }

    @PutMapping("/update/player/{id}")
    public PlayerModel updatePlayer(@PathVariable Integer id, @RequestBody PlayerModel playerModel) {
        if (playerModel.getPlayerId() != id || !playerService.findById(id).isPresent()) {
            return null;
        }

        return playerService.save(playerModel);
    }

    @DeleteMapping("/delete/player/{id}")
    public PlayerModel deletePlayer(@PathVariable Integer id) {
        Optional<PlayerModel> player = playerService.findById(id);
        if (!player.isPresent()) {
            return null;
        }

        playerService.deleteById(id);

        return player.get();
    }
}
