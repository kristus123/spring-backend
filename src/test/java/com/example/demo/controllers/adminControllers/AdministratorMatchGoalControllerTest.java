package com.example.demo.controllers.adminControllers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
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
@ActiveProfiles("test")
class AdministratorMatchGoalControllerTest {

    @Autowired
    MockMvc mockMvc;

    String jsonBody;
    static int ID = 0;

    //@BeforeEach
    void setUp() throws Exception {
        jsonBody = "{\"playerId\": 1,\"goalType\": 1, \"matchId\": 1, \"description\": \"Incredible goal!\" }";

        mockMvc.perform(post("/v1/admin/post/matchgoal").contentType(MediaType.APPLICATION_JSON).
                content(jsonBody)).
                andExpect(status().isCreated());
        ID++;
    }

    //@Test
    void testThatMatchGoalIsDeleted() throws Exception {
        mockMvc.perform(delete("/v1/admin/delete/matchgoal/" + ID--));
        // This is not a good testing method
        // Should be checked by http status set by a custom exception
        // We are actually expecting a nullPointerException, but it is wrapped inside a Spring exception
        /*
        assertThrows(NestedServletException.class, () ->
                mockMvc.perform(get("/v1/common/get/matchgoal/" + ID)));

         */
    }

    //@Test
    void testThatMatchGoalIsUpdated() throws Exception {
        String jsonBodyUpdated = "{\"playerId\": 2,\"goalType\": \"PENALTY\", \"matchId\": 1, \"description\": \"Not so Incredible goal!\" }";


        mockMvc.perform(put("/v1/admin/update/matchgoal/" + 1).contentType(MediaType.APPLICATION_JSON).
                content(jsonBodyUpdated))
                .andExpect(status().isCreated());


        mockMvc.perform(get("/v1/common/get/matchgoal/" + 1));//.andExpect(jsonPath("$.description").value("Not so Incredible goal!"));
    }
}