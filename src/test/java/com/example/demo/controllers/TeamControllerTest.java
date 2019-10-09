package com.example.demo.controllers;

import com.example.demo.exceptions.TeamNotFoundException;
import com.example.demo.models.TeamModel;
import com.example.demo.services.TeamService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
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

import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
class TeamControllerTest {

    private MockMvc mockMvc;

    @MockBean
    private TeamService teamServiceMock;

    @Autowired
    private WebApplicationContext webApplicationContext;



    @BeforeEach
    public void setUp() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }


    @Test
    void newTeam() throws Exception {
        Integer id = 1;
        TeamModel team = new TeamModel(id, id, "ManU");

        when(teamServiceMock.save(any(TeamModel.class))).thenReturn(team);
        mockMvc.perform(post("/v1/admin/post/team")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(team))
                .accept(MediaType.APPLICATION_JSON))
                //.andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.teamId").value(id));
    }

    @Test
    void updateTeam() throws Exception {

        Integer id = 1;
        Optional<TeamModel> team = Optional.of(new TeamModel(id, id, "ManU"));
        when(teamServiceMock.findById(id)).thenReturn(team);

        Optional<TeamModel> updatedTeam = Optional.of(new TeamModel(id, id, "Chelsea"));
        when(teamServiceMock.save(any(TeamModel.class))).thenReturn(updatedTeam.get());

        mockMvc.perform(put("/v1/admin/update/team/{id}", id)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(updatedTeam.get()))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.association.name").value("Chelsea"));
    }

    @Test
    void updateInvalidTeam() throws Exception {

        Integer pathId = 1;
        Integer id = 2;

        Optional<TeamModel> team = Optional.of(new TeamModel(id, id, "ManU"));

        mockMvc.perform(put("/v1/admin/update/team/{id}", pathId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(team.get()))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    void updateNonExistingTeam() throws Exception {

        Integer pathId = 1;
        Integer id = 2;
        Optional<TeamModel> team = Optional.of(new TeamModel(id, id, "ManU"));

        when(teamServiceMock.findById(pathId)).thenReturn(Optional.empty());

        mockMvc.perform(put("/v1/admin/update/team/{id}", id)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(team.get()))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    void deleteTeam() throws Exception {
        Integer id = 1;
        Optional<TeamModel> team = Optional.of(new TeamModel(id, id, "ManU"));

        when(teamServiceMock.findById(id)).thenReturn(team);
        doNothing().when(teamServiceMock).deleteById(id);
        mockMvc.perform(delete("/v1/admin/delete/team/{id}", id))
                .andExpect(status().isOk());
    }

    @Test
    void deleteNonExistingTeam() throws Exception {
        Integer id = 1;

        when(teamServiceMock.findById(id)).thenReturn(Optional.empty());
        doNothing().when(teamServiceMock).deleteById(id);
        mockMvc.perform(delete("/v1/admin/delete/team/{id}", id))
                .andExpect(status().isNotFound());
    }

    @Test
    void oneTeam() throws Exception {
        Integer id = 1;
        Optional<TeamModel> team = Optional.of(new TeamModel(id, id, "ManU"));
        when(teamServiceMock.findById(id)).thenReturn(team);

        mockMvc.perform(get("/v1/user/get/team/{id}", id)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.teamId").value(id));
    }

    @Test
    void allTeams() throws Exception {
        Integer id = 1, id2 = 2;
        TeamModel team1 = new TeamModel(id, id, "ManU");
        TeamModel team2 = new TeamModel(id2, id2, "Chelsea");
        List<TeamModel> teams = Arrays.asList(team1, team2);

        when(teamServiceMock.findAll()).thenReturn(teams);

        mockMvc.perform(get("/v1/user/get/teams"))
                .andExpect(status().isOk())
                //.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$._embedded.teamModelList[0].teamId", is(1)))
                .andExpect(jsonPath("$._embedded.teamModelList[0].association.name", is("ManU")))
                .andExpect(jsonPath("$._embedded.teamModelList[1].teamId", is(2)))
                .andExpect(jsonPath("$._embedded.teamModelList[1].association.name", is("Chelsea")));
    }

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}