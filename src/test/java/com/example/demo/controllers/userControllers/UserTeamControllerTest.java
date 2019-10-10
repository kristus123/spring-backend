package com.example.demo.controllers.userControllers;

import com.example.demo.models.TeamModel;
import com.example.demo.services.TeamService;
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
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
class UserTeamControllerTest {

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
}