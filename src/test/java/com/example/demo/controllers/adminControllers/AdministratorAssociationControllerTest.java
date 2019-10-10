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
        getAssociation();
        updateAssociation();
        deleteAssociation();
    }


    void addAssociation() throws Exception {
        String json = "{\"association_id\" : 1, \"association_name\" : \"This name\", \"description\" : \"This description\" }";
        mockMvc.perform(post("/v1/admin/post/association")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andDo(print())
                .andExpect(status().isOk());
    }


    void getAssociation() throws Exception {
        mockMvc.perform(get("/v1/admin/get/association/1"))
                .andDo(print())
                .andExpect(content().json("{\"association_id\" : 1, \"association_name\" : \"This name\", \"description\" : \"This description\"}"));
                //.andExpect(status().isOk());
    }


    void updateAssociation() throws Exception {
        String json = "{\"association_id\" : 1, \"association_name\" : \"Updated name\", \"description\" : \"Updated description\"}";
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
