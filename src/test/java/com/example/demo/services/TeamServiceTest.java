package com.example.demo.services;

import com.example.demo.models.TeamModel;
import static org.mockito.Matchers.any;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;
import static org.hamcrest.Matchers.*;

@RunWith(SpringRunner.class)
@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class TeamServiceTest {

    MockMvc mockMvc;

    @MockBean
    TeamService teamServiceMock;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @BeforeEach
    public void setUp() {
        this.mockMvc = webAppContextSetup(webApplicationContext).build();
    }

    @Test
    void deleteTest() throws Exception {
        Integer id = 1;
        Optional<TeamModel> team = Optional.of(new TeamModel(id, id, "ManU"));

        when(teamServiceMock.findById(id)).thenReturn(team);
        doNothing().when(teamServiceMock).deleteById(id);
        mockMvc.perform(delete("/v1/admin/delete/team/" + id))
                .andExpect(status().isOk());
    }

    @Test
    void findAll() throws Exception {

        Integer id = 1, id2 = 2;
        TeamModel team1 = new TeamModel(id, id, "ManU");
        TeamModel team2 = new TeamModel(id2, id2, "Chelsea");
        List<TeamModel> teams = Arrays.asList(team1, team2);

        when(teamServiceMock.findAll()).thenReturn(teams);

        mockMvc.perform(get("/v1/user/get/teams"))
                .andExpect(status().isOk())
                //.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                //.andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$._embedded.teamModelList[0].team_id", is(1)))
                .andExpect(jsonPath("$._embedded.teamModelList[0].association.name", is("ManU")))
                .andExpect(jsonPath("$._embedded.teamModelList[1].team_id", is(2)))
                .andExpect(jsonPath("$._embedded.teamModelList[1].association.name", is("Chelsea")));
    }

    @Test
    void findById() throws Exception {

        Integer id = 1;
        Optional<TeamModel> team = Optional.of(new TeamModel(id, id, "ManU"));
        when(teamServiceMock.findById(id)).thenReturn(team);

        mockMvc.perform(get("/v1/user/get/team/" + id)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.team_id").value(id));
    }


    @Test
    void save() throws Exception {

        Integer id = 1;
        TeamModel team = new TeamModel(id, id, "ManU");

        when(teamServiceMock.save(any(TeamModel.class))).thenReturn(team);
        mockMvc.perform(post("/v1/admin/post/team")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(team))
                .accept(MediaType.APPLICATION_JSON))
                //.andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.team_id").value(id));
    }


    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}