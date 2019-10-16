package com.example.demo.controllers.adminControllers;

import com.example.demo.repositories.AssociationRepository;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

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
        String json = "{\"associationId\" : 1, \"name\" : \"This name\", \"description\" : \"This description\" }";
        mockMvc.perform(post("/v1/admin/post/association")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andDo(print())
                .andExpect(status().isOk());
    }

    void updateAssociation() throws Exception {
        String json = "{\"associationId\" : 1, \"name\" : \"Updated name\", \"description\" : \"Updated description\"}";
        mockMvc.perform(put("/v1/admin/update/association/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andDo(print())
                .andExpect(status().isOk());
    }

    void deleteAssociation() throws Exception {
        mockMvc.perform(delete("/v1/admin/delete/association/1"))
                .andDo(print())
                .andExpect(status().isOk());
    }
}
