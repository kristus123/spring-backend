package com.example.demo.controllers.commonControllers;

import com.example.demo.models.TeamModel;
import com.example.demo.services.TeamService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
class CommonTeamControllerTest {

    @Autowired
    MockMvc mockMvc;


    @Test
    void getTeam() throws Exception {
        Integer id = 1;
        TeamModel team = new TeamModel(id, id, "ManU");

        mockMvc.perform(post("/v1/admin/post/team")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(team))
                .accept(MediaType.APPLICATION_JSON))
                //.andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8));

        mockMvc.perform(get("/v1/common/get/team/{id}", id))
                //.accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
                //.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))


    }

    @Test
    void getNonExistingTeam() throws Exception {
        Integer id = 10;

        mockMvc.perform(get("/v1/common/get/team/{id}", id))
                .andExpect(jsonPath("$").doesNotExist());
    }

    @Test
    void getTeams() throws Exception {
        Integer id = 1;
        TeamModel team = new TeamModel(id, id, "ManU");

        mockMvc.perform(post("/v1/admin/post/team")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(team))
                .accept(MediaType.APPLICATION_JSON))
                //.andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8));

        mockMvc.perform(get("/v1/common/get/team"))
                .andExpect(status().isOk());
                //.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))

    }

    @Test
    void getNonExistingTeams() throws Exception {
        mockMvc.perform(get("/v1/common/get/team"))
                .andExpect(status().isOk());
                //.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                //.andExpect(jsonPath("$").doesNotExist());
    }


/*
    @Test
    void getEmptyTeams() throws Exception {

        mockMvc.perform(get("/v1/common/get/team"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").doesNotExist());

    }

 */



    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}