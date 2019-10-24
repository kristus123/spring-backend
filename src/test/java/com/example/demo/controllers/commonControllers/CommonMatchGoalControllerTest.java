package com.example.demo.controllers.commonControllers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class CommonMatchGoalControllerTest {

    @Autowired
    MockMvc mockMvc;

    String jsonBody;
    static int ID = 1;

    @BeforeEach
    void setUp() throws Exception {
        jsonBody = "{\"playerId\": 2,\"goalType\": \"SCORPION_KICK\", \"matchId\": 1, \"description\": \"Incredible goal!\" }";

        mockMvc.perform(post("/v1/admin/post/matchgoal").contentType(MediaType.APPLICATION_JSON).
                content(jsonBody)).
                andExpect(status().isCreated());
        ID++;
    }

    //@Test
    void getMatchGoal() {
    }

    //@Test
    void getMatchGoals() {
    }

    //@Test
    void testThatCanGetMatchGoalAfterPost() throws Exception {
        mockMvc.perform(get("/v1/common/get/matchgoal/" + 2)).andExpect(content().json("{\"description\": \"Incredible goal!\" }"));
    }

    @Test
    void testThatCanGetAllMatchGoals() throws Exception {
        mockMvc.perform(get("/v1/common/get/matchgoal/")).andExpect(jsonPath("$").isNotEmpty());
    }
}