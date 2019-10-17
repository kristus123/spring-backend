package com.example.demo.controllers.userControllers;

import com.example.demo.exceptions.PlayerNotFoundException;
import com.example.demo.models.PlayerModel;
import com.example.demo.services.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/v1/user")
public class UserPlayerController {

    @Autowired
    private PlayerService playerService;


    @GetMapping("/get/player/{id}")
    public PlayerModel onePlayer(@PathVariable Integer id) {

        Optional<PlayerModel> player = playerService.findById(id);
        if (!player.isPresent())
            return null;

        return player.get();
    }

    @PostMapping("/add/player")
    public PlayerModel add(@RequestBody PlayerModel player) {
        return playerService.save(player);
    }

    @GetMapping("/get/player")
    public List<PlayerModel> allPlayers() {
        return playerService.findAll();
    }



}
