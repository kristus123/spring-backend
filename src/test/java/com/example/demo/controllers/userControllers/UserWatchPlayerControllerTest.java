package com.example.demo.controllers.userControllers;

import com.example.demo.models.PlayerModel;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class UserWatchPlayerControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Test
    void getPlayer() {
    }

    @Test
    void getPlayers() {
    }

    @Test
    void addPlayer() throws Exception {
    }

    @Test
    void deletePlayer() {
    }
}