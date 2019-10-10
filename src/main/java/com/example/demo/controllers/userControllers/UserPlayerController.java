package com.example.demo.controllers.userControllers;

import com.example.demo.exceptions.PlayerNotFoundException;
import com.example.demo.models.PlayerModel;
import com.example.demo.services.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/user")
public class UserPlayerController {

    @Autowired
    private PlayerService playerService;


    @GetMapping("/get/player/{id}")
    public PlayerModel onePlayer(@PathVariable Integer id) {
        return playerService.findById(id).get();
    }

    @GetMapping("/get/player")
    public List<PlayerModel> allPlayers() {

        List<PlayerModel> players = playerService.findAll();

        return players;
    }



}
