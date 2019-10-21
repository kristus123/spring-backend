package com.example.demo.controllers.adminControllers;

import com.example.demo.repositories.AssociationRepository;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import org.json.JSONObject;


@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class AdministratorAssociationControllerTest {
    @Autowired
    MockMvc mockMvc;

    @Autowired
    AssociationRepository associationRepository;

    @Test
    void runTests() throws Exception {
        addAssociation();
        updateAssociation();
        //deleteAssociation();
    }


    void addAssociation() throws Exception {
        String json = "{\"name\" : \"This name\", \"description\" : \"This description\" }";
        mockMvc.perform(post("/v1/admin/post/association")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andDo(print())
                .andExpect(status().isCreated());
    }

    void updateAssociation() throws Exception {
        String json = new JSONObject()
                .put("name", "Updated name")
                .put("description", "Updated description").toString();
        mockMvc.perform(put("/v1/admin/update/association/{id}", 1)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andDo(print())
                .andExpect(status().isCreated());
    }

    void deleteAssociation() throws Exception {
        mockMvc.perform(delete("/v1/admin/delete/association/1"))
                .andDo(print())
                .andExpect(status().isOk());
    }
}
