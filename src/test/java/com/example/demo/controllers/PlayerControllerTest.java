package com.example.demo.controllers;

import com.example.demo.models.PlayerModel;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class PlayerControllerTest {

    @Autowired
    PlayerController playerController;

    // TODO PANDA: not done...

    @Test
    void newPlayer() {
    }


    @Test
    void onePlayer() {

        /*
        PlayerModel player = new PlayerModel();
        Resource<PlayerModel> returned = playerController.onePlayer(player.getPlayer_id());

         */
    }

    @Test
    void allPlayers() {

        /*
        Resources<Resource<PlayerModel>> players = playerController.allPlayers();

         */


    }



}