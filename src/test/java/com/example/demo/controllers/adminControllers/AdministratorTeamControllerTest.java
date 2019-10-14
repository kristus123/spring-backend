package com.example.demo.controllers.adminControllers;

import com.example.demo.models.TeamModel;
import com.example.demo.services.TeamService;
import com.fasterxml.jackson.databind.ObjectMapper;
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

//@ExtendWith(SpringExtension.class)
//@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
//@AutoConfigureMockMvc
class AdministratorTeamControllerTest {

    @Autowired
    private MockMvc mockMvc;

    //@Test
    void newTeam() throws Exception {
        Integer id = 1;
        TeamModel team = new TeamModel(id, id, "ManU");

        //when(teamServiceMock.save(any(TeamModel.class))).thenReturn(team);
        mockMvc.perform(post("/v1/admin/post/team")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(team))
                .accept(MediaType.APPLICATION_JSON))
                //.andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.teamId").value(id));
    }

    //@Test
    void updateTeam() throws Exception {

        Integer id = 1;
        Optional<TeamModel> team = Optional.of(new TeamModel(id, id, "ManU"));
        //when(teamServiceMock.findById(id)).thenReturn(team);

        Optional<TeamModel> updatedTeam = Optional.of(new TeamModel(id, id, "Chelsea"));
        //when(teamServiceMock.save(any(TeamModel.class))).thenReturn(updatedTeam.get());

        mockMvc.perform(put("/v1/admin/update/team/{id}", id)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(updatedTeam.get()))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.association.name").value("Chelsea"));
    }

    //@Test
    void updateInvalidTeam() throws Exception {

        Integer pathId = 1;
        Integer id = 2;

        Optional<TeamModel> team = Optional.of(new TeamModel(id, id, "ManU"));

        mockMvc.perform(put("/v1/admin/update/team/{id}", pathId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(team.get()))
                .accept(MediaType.APPLICATION_JSON));
    }

    //@Test
    void updateNonExistingTeam() throws Exception {

        Integer pathId = 1;
        Integer id = 2;
        Optional<TeamModel> team = Optional.of(new TeamModel(id, id, "ManU"));

        //when(teamServiceMock.findById(pathId)).thenReturn(Optional.empty());

        mockMvc.perform(put("/v1/admin/update/team/{id}", id)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(team.get()))
                .accept(MediaType.APPLICATION_JSON));
    }

    //@Test
    void deleteTeam() throws Exception {
        Integer id = 1;
        Optional<TeamModel> team = Optional.of(new TeamModel(id, id, "ManU"));

        //when(teamServiceMock.findById(id)).thenReturn(team);
        //doNothing().when(teamServiceMock).deleteById(id);
        mockMvc.perform(delete("/v1/admin/delete/team/{id}", id))
                .andExpect(status().isOk());
    }

    //@Test
    void deleteNonExistingTeam() throws Exception {
        Integer id = 1;

        //when(teamServiceMock.findById(id)).thenReturn(Optional.empty());
        //doNothing().when(teamServiceMock).deleteById(id);
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