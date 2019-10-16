package com.example.demo.controllers.commonControllers;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
class CommonAssociationControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Test
    void getAssociation() throws Exception {
        String json = "{\"associationId\" : 1, \"name\" : \"This name\", \"description\" : \"This description\" }";
        mockMvc.perform(post("/v1/admin/post/association")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andDo(print())
                .andExpect(status().isOk());

        mockMvc.perform(get("/v1/common/get/association/1"))
                .andDo(print())
                .andExpect(content().json("{\"associationId\" : 1, \"name\" : \"This name\", \"description\" : \"This description\"}"))
                .andExpect(status().isOk());
    }

    @Test
    void allAssociations() {
    }
}