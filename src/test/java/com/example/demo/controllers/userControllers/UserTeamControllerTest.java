package com.example.demo.controllers.userControllers;

import com.example.demo.models.TeamModel;
import com.example.demo.services.TeamService;
import org.junit.Before;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@Disabled
@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
class UserTeamControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Mock
    TeamService teamService;



    @Test
    void oneTeam() throws Exception {
        Integer id = 1;
        Optional<TeamModel> team = Optional.of(new TeamModel(id, id, "ManU"));
        when(teamService.findById(id)).thenReturn(team);



        mockMvc.perform(get("/v1/user/get/team/{id}", id)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.teamId").value(id));
    }

    @Test
    void oneNonExistingTeam() throws Exception {
        Integer id = 1;
        when(teamService.findById(id)).thenReturn(Optional.empty());

        mockMvc.perform(get("/v1/user/get/team/{id}", id));
    }

    @Test
    void allTeams() throws Exception {
        Integer id = 1, id2 = 2;
        TeamModel team1 = new TeamModel(id, id, "ManU");
        TeamModel team2 = new TeamModel(id2, id2, "Chelsea");
        List<TeamModel> teams = Arrays.asList(team1, team2);

        when(teamService.findAll()).thenReturn(teams);

        mockMvc.perform(get("/v1/user/get/team"))
                .andExpect(status().isOk())
                //.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$[0].teamId", is(1)))
                .andExpect(jsonPath("$[0].association.name", is("ManU")))
                .andExpect(jsonPath("$[1].teamId", is(2)))
                .andExpect(jsonPath("$[1].association.name", is("Chelsea")));
    }
}