package com.example.demo.controllers.commonControllers;

import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;


@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
class CommonPlayerControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Test
    void getPlayer() throws Exception {
        JSONObject json = new JSONObject()
                .put("personId", 3)
                .put("teamId", 3)
                .put("normalPosition", "Shooter")
                .put("playerNumber", 11)
                .put("playername", "Ramoirs");

        System.out.println(json.toString());

        mockMvc.perform(get("/v1/common/get/player/1"))
                .andDo(print())
                .andDo(p -> {
                    System.out.println(content());
                });
        //.andExpect(content().json(json.toString()));
    }

    @Test
    void allPlayers() {
    }
}