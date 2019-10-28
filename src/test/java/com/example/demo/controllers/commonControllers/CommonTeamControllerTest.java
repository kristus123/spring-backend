package com.example.demo.controllers.commonControllers;

import com.example.demo.dtos.TeamDTO;
import com.example.demo.LifeHack;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class CommonTeamControllerTest {

    @Autowired
    MockMvc mockMvc;


    //@Test
    void getTeam() throws Exception {

        Integer id = 1;
        TeamDTO team = new TeamDTO(1, 1, 1, 1);

        mockMvc.perform(post("/v1/admin/post/team")
                .contentType(MediaType.APPLICATION_JSON)
                .content(LifeHack.asJsonString(team))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8));

        mockMvc.perform(get("/v1/common/get/team/{id}", id)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8));
    }

    //@Test
    void getNonExistingTeam() throws Exception {
        mockMvc.perform(get("/v1/common/get/team/{id}", -1))
                .andExpect(status().isNotFound());
    }

    //@Test
    void getTeams() throws Exception {
        TeamDTO team = new TeamDTO(1, 1, 1, 1);

        mockMvc.perform(post("/v1/admin/post/team")
                .contentType(MediaType.APPLICATION_JSON)
                .content(LifeHack.asJsonString(team))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8));

        mockMvc.perform(get("/v1/common/get/team")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8));
    }

    //@Test
    void getNonExistingTeams() throws Exception {

        // delete everything first, then..

        mockMvc.perform(get("/v1/common/get/team"));
                //.andExpect(status().isNotFound());
    }

}