package com.example.demo.controllers.adminControllers;

import com.example.demo.repositories.PlayerRepository;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class AdministratorPlayerControllerTest {
    @Autowired
    MockMvc mockMvc;

    @Autowired
    PlayerRepository playerRepository;

    @Before
    void addDummyData() throws Exception {
        String json = "{\"personId\" : 2, \"teamId\" : 3, \"normalPosition\" : \"Attacker\", \"playerNumber\" : 7 }";
        mockMvc.perform(post("/v1/admin/post/player")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    void addPlayer() throws Exception {
        String json = "{\"personId\" : 3, \"teamId\" : 3, \"normalPosition\" : \"Shooter\", \"playerNumber\" : 11 }";
        mockMvc.perform(post("/v1/admin/post/player")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    void getPlayer() throws Exception {
        mockMvc.perform(get("/v1/admin/get/player/1"))
                .andDo(print())
                .andExpect(content().json("{\"playerId\" : 1, \"person\" : null, \"team\" : null, \"normalPosition\" : \"Shooter\", \"playerNumber\" : 11}"));
    }

    @Test
    void updatePlayer() throws Exception {
        String json = "{\"playerId\" : 1, \"person\" : null, \"team\" : null, \"normalPosition\" : \"Defender\", \"playerNumber\" : 21 }";
        mockMvc.perform(put("/v1/admin/update/player/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    void deletePlayer() throws Exception {
        mockMvc.perform(delete("/v1/admin/delete/player/1"))
                .andDo(print())
                .andExpect(status().isOk());
    }
}
