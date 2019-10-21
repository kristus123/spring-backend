package com.example.demo.controllers.adminControllers;

import com.example.demo.dtos.TeamDTO;
import com.example.demo.models.TeamModel;
import com.example.demo.services.LifeHack;
import com.example.demo.services.TeamService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.core.IsNull;
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

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class AdministratorTeamControllerTest {


    /**
     *  NB!!! TESTS DEPEND ON THE DB CREATED THROUGH DUMMYDATAFILLER .-.
     */


    @Autowired
    MockMvc mockMvc;



    @Test
    void testThatTeamIsCreated() throws Exception {

        Integer id = 1;
        TeamDTO team = new TeamDTO(1, 1, 1, 1);

        mockMvc.perform(post("/v1/admin/post/team")
                .contentType(MediaType.APPLICATION_JSON)
                .content(LifeHack.asJsonString(team))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$").exists())
                .andExpect(jsonPath("$.active").value(is(true)));
    }

    void testThatCreateFailsForFaultyInput() throws Exception {
        mockMvc.perform(post("/v1/admin/post/team")
                .contentType(MediaType.APPLICATION_JSON)
                .content(LifeHack.asJsonString(null))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testThatTeamIsUpdated() throws Exception {

        Integer id = 1;
        TeamDTO team = new TeamDTO(2, 1, 1, 1);

        mockMvc.perform(put("/v1/admin/update/team/{id}", id)
                .contentType(MediaType.APPLICATION_JSON)
                .content(LifeHack.asJsonString(team))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8));
    }

    @Test
    void testThatUpdateFailsForNonExistingTeam() throws Exception {

        Integer pathId = -1;
        TeamDTO team = new TeamDTO(1, 1, 1, 1);

        mockMvc.perform(put("/v1/admin/update/team/{id}", pathId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(LifeHack.asJsonString(team))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    void testThatUpdateFailsForFaultyInput() throws Exception {
        mockMvc.perform(put("/v1/admin/update/team/{id}", 1)
                .contentType(MediaType.APPLICATION_JSON)
                .content(LifeHack.asJsonString(""))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }


    @Test
    void testThatTeamIsDeleted() throws Exception {

        TeamDTO team = new TeamDTO(1, 1, 1, 1);

        mockMvc.perform(post("/v1/admin/post/team")
                .contentType(MediaType.APPLICATION_JSON)
                .content(LifeHack.asJsonString(team))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8));

        mockMvc.perform(delete("/v1/admin/delete/team/{id}", 1))
                .andExpect(status().isOk());
    }

    @Test
    void testThatDeleteFailsForNonExistingTeam() throws Exception {
        mockMvc.perform(delete("/v1/admin/delete/team/{id}", -1))
                .andExpect(status().isNotFound());
    }

}