package com.example.demo.controllers.adminControllers;

import com.example.demo.dtos.TeamDTO;
import com.example.demo.models.TeamModel;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class AdministratorTeamControllerTest {

    @Autowired
    MockMvc mockMvc;


    //@Test
    void addTeam() throws Exception {
        Integer id = 1;
        TeamDTO team = new TeamDTO(1, 1, 1, 1, 1);

        mockMvc.perform(post("/v1/admin/post/team")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(team))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
    }

    //@Test
    void updateTeam() throws Exception {

        Integer id = 1;

        TeamModel team = new TeamModel(id, id, "ManU");
        mockMvc.perform(post("/v1/admin/post/team")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(team))
                .accept(MediaType.APPLICATION_JSON))
                //.andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8));

        TeamModel updatedTeam = new TeamModel(id, id, "Chelsea");
        mockMvc.perform(put("/v1/admin/update/team/{id}", id)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(updatedTeam))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8));
    }

    //@Test
    void updateWrongTeam() throws Exception {

        Integer pathId = 1;
        Integer id = 2;

        TeamModel team = new TeamModel(id, id, "ManU");

        mockMvc.perform(put("/v1/admin/update/team/{id}", pathId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(team))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").doesNotExist());
    }

    //@Test
    void updateEmptyTeam() throws Exception {

        Integer id = 1;

        mockMvc.perform(put("/v1/admin/update/team/{id}", id)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(Optional.empty()))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").doesNotExist());
    }

    //@Test
    void updateNonExistingTeam() throws Exception {

        Integer id = 10;
        TeamModel team = new TeamModel(id, id, "ManU");

        mockMvc.perform(put("/v1/admin/update/team/{id}", id)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(team))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").doesNotExist());
    }

    //@Test
    void deleteTeam() throws Exception {
        Integer id = 1;
        TeamModel team = new TeamModel(id, id, "ManU");

        mockMvc.perform(post("/v1/admin/post/team")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(team))
                .accept(MediaType.APPLICATION_JSON))
                //.andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8));

        mockMvc.perform(delete("/v1/admin/delete/team/{id}", id))
                .andExpect(status().isOk());
    }

    //@Test
    void deleteNonExistingTeam() throws Exception {
        Integer id = 1;

        mockMvc.perform(delete("/v1/admin/delete/team/{id}", id));
    }


    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}