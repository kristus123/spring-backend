package com.example.demo.controllers.adminControllers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.util.NestedServletException;

import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class AdministratorMatchGoalControllerTest {

    @Autowired
    MockMvc mockMvc;

    String jsonBody;
    static int ID = 0;

    @BeforeEach
    void setUp() throws Exception {
        jsonBody = "{\"playerId\": 1,\"goalTypeId\": 1, \"matchId\": 1, \"description\": \"Incredible goal!\" }";

        mockMvc.perform(post("/v1/admin/post/matchgoal").contentType(MediaType.APPLICATION_JSON).
                content(jsonBody)).
                andExpect(status().isOk());
        ID++;
    }

    @Test
    void testThatCanGetMatchGoalAfterPost() throws Exception {
        mockMvc.perform(get("/v1/admin/get/matchgoal/" + ID)).andExpect(content().json("{\"description\": \"Incredible goal!\" }"));
    }

    @Test
    void testThatCanGetAllMatchGoals() throws Exception {
        mockMvc.perform(get("/v1/admin/get/matchgoal/")).andExpect(jsonPath("$").isNotEmpty());
    }

    @Test
    void testThatMatchGoalIsDeleted() throws Exception {
        mockMvc.perform(delete("/v1/admin/delete/matchgoal/" + ID));
        // This is not a good testing method
        // Should be checked by http status set by a custom exception
        // We are actually expecting a nullPointerException, but it is wrapped inside a Spring exception
        assertThrows(NestedServletException.class, () ->
                mockMvc.perform(get("/v1/admin/get/matchgoal/" + ID)));
    }

    @Test
    void testThatMatchGoalIsUpdated() throws Exception {
        String jsonBodyUpdated = "{\"goalId\": " + ID + ",\"playerId\": 1,\"goalTypeId\": 1, \"matchId\": 1, \"description\": \"Not so Incredible goal!\" }";

        mockMvc.perform(put("/v1/admin/update/matchgoal/" + ID).contentType(MediaType.APPLICATION_JSON).
                content(jsonBodyUpdated));

        mockMvc.perform(get("/v1/admin/get/matchgoal/" + ID)).andExpect(content().json("{\"description\": \"Not so Incredible goal!\" }"));
    }
}