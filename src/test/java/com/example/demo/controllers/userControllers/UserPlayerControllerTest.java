package com.example.demo.controllers.userControllers;

import com.example.demo.models.PlayerModel;
import com.example.demo.services.PlayerService;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;


@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
class UserPlayerControllerTest {
    @Autowired
    MockMvc mockMvc;

    @Autowired PlayerService playerService;

    @Test
    void onePlayer() throws Exception {
        PlayerModel player = playerService.findById(2).get();

        String json = new JSONObject()
                .put("playerId", player.getPlayerId())
                .put("playername", player.getPerson().getFirstName() + " " + player.getPerson().getLastName()).toString();
        mockMvc.perform(get("/v1/admin/get/player/2"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(content().json(json));

            //.andExpect(content().json(json));
    }


    @Test
    void browsePlayers() throws Exception {
        mockMvc.perform(get("/v1/browse/matches"))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    void allPlayers() {
    }
}