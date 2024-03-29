package com.example.demo.controllers.adminControllers;

import com.example.demo.models.PlayerModel;
import com.example.demo.repositories.PlayerRepository;
/*
import com.vladmihalcea.hibernate.type.range.Range;
import org.hibernate.envers.AuditReader;
import org.hibernate.envers.internal.reader.AuditReaderImpl;
import org.hibernate.envers.query.AuditEntity;
import org.hibernate.envers.query.AuditQuery;
import org.hibernate.envers.query.AuditQueryCreator;

 */
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.time.LocalDate;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@Transactional
public class AdministratorPlayerControllerTest {
    @Autowired
    MockMvc mockMvc;

    @Autowired
    PlayerRepository playerRepository;

    //@Before
    void addDummyData() throws Exception {
        //String json = "{\"personId\" : 2, \"teamId\" : 3, \"normalPosition\" : \"Attacker\", \"playerNumber\" : 7 }";
        String json = new JSONObject()
                .put("personId", 2)
                .put("teamId", 3)
                .put("normalPosition", "Attacker")
                .put("playerNumber", 7).toString();

        mockMvc.perform(post("/v1/admin/post/player")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andDo(print())
                .andExpect(status().isOk());
    }

    //@Test
    void addPlayer() throws Exception {
        JSONObject json = new JSONObject()
                .put("personId", 1)
                .put("teamId", 1)
                .put("normalPosition", "Shooter")
                .put("playerNumber", 11)
                .put("playername", "Ramoirs");
        //String json = "{\"personId\" : 3, \"teamId\" : 3, \"normalPosition\" : \"Shooter\", \"playerNumber\" : 11 }";
        mockMvc.perform(post("/v1/admin/post/player")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json.toString()))
                .andDo(print())
                .andExpect(status().isCreated());
    }

    //@Test
    void updatePlayer() throws Exception {
        JSONObject json = new JSONObject()
                .put("personId", 1)
                .put("teamId", 1)
                .put("normalPosition", "Defender")
                .put("playerNumber", 21)
                .put("playername", "Ronaldo");

        mockMvc.perform(put("/v1/admin/update/player/2")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json.toString()))
                .andDo(print())
                .andExpect(status().isCreated());
    }

    //@Test
    void deletePlayer() throws Exception {
        mockMvc.perform(delete("/v1/admin/delete/player/2"))
                .andDo(print())
                .andExpect(status().isOk());
    }
}
